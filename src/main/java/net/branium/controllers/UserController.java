package net.branium.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.user.*;
import net.branium.services.UserService;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/info")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentInfo() {
        StudentResponse response = userService.getStudentInfo();
        var responseBody = ApiResponse.<StudentResponse>builder()
                .message("get student info successful")
                .result(response)
                .build();
        return ResponseEntity.ok(responseBody);
    }

    // TODO: validate the request body
    @PutMapping(path = "/info")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudentInfo(@RequestBody @Valid StudentUpdateRequest request) {
        StudentResponse response = userService.updateStudentInfo(request);
        var responseBody = ApiResponse.<StudentResponse>builder()
                .message("update student info successful")
                .result(response)
                .build();
        return ResponseEntity.ok(responseBody);
    }
}
