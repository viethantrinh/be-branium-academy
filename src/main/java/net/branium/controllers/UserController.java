package net.branium.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.User;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;
import net.branium.mappers.UserMapper;
import net.branium.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping

    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody @Valid UserCreateRequest request) {
        UserResponse response = userService.createUser(request);
        var responseBody = ApiResponse.<UserResponse>builder()
                .message("create user success")
                .result(response)
                .build();
        return new ResponseEntity<ApiResponse<UserResponse>>(responseBody, HttpStatus.CREATED);
    }

}
