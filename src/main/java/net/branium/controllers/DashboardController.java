package net.branium.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.file.FileResponse;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;
import net.branium.services.FileService;
import net.branium.services.UserService;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final UserService userService;
    private final FileService fileService;

    // TODO: validate the request body
    @PostMapping(path = "/users")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody @Valid UserCreateRequest request) {
        UserResponse response = userService.createUser(request);
        var responseBody = ApiResponse.<UserResponse>builder()
                .message("create user success")
                .result(response)
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable(name = "id") @UUID(message = "ID must be an UUID string") String id) {
        UserResponse response = userService.getUserById(id);
        var responseBody = ApiResponse.<UserResponse>builder()
                .message("get user by id success")
                .result(response)
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping(path = "/users")
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
    @PutMapping(path = "/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserById(@PathVariable(name = "id") String id,
                                                                    @RequestBody @Valid UserUpdateRequest request) {
        UserResponse response = userService.updateUser(id, request);
        var responseBody = ApiResponse.<UserResponse>builder()
                .message("update user success")
                .result(response)
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUserById(@PathVariable(name = "id") String id) {
        userService.deleteUserById(id);
        var responseBody = ApiResponse.<UserResponse>builder()
                .message("delete user by id successful")
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/users/{id}/avatar")
    public ResponseEntity<ApiResponse<FileResponse>> createUserAvatarById(@PathVariable(name = "id") String id,
                                                               @RequestParam(name = "file") MultipartFile multipartFile) {
        FileResponse file = fileService.uploadUserAvatarById(id, multipartFile);
        ApiResponse<FileResponse> response = ApiResponse.<FileResponse>builder()
                .message("upload user's avatar successful")
                .result(file)
                .build();
        return ResponseEntity.ok(response);
    }
}
