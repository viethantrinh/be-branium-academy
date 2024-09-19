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

    // TODO: validate the request body
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody @Valid UserCreateRequest request) {
        UserResponse response = userService.createUser(request);
        var responseBody = ApiResponse.<UserResponse>builder()
                .message("create user success")
                .result(response)
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable(name = "id") @UUID(message = "ID must be an UUID string") String id) {
        UserResponse response = userService.getUserById(id);
        var responseBody = ApiResponse.<UserResponse>builder()
                .message("get user by id success")
                .result(response)
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> listAllUsers() {
        List<UserResponse> response = userService.getAllUsers();
        var responseBody = ApiResponse.<List<UserResponse>>builder()
                .message("get all users success")
                .result(response)
                .build();
        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(responseBody);
    }

    // TODO: validate the request body
    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserById(@PathVariable(name = "id") String id,
                                                                    @RequestBody @Valid UserUpdateRequest request) {
        UserResponse response = userService.updateUser(id, request);
        var responseBody = ApiResponse.<UserResponse>builder()
                .message("update user success")
                .result(response)
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUserById(@PathVariable(name = "id") String id) {
        userService.deleteUserById(id);
        var responseBody = ApiResponse.<UserResponse>builder()
                .message("delete user by id successful")
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.NO_CONTENT);
    }

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
