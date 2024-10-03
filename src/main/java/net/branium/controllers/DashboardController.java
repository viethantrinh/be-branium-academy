package net.branium.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.branium.domains.Course;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.course.CourseCreateRequest;
import net.branium.dtos.course.CourseResponse;
import net.branium.dtos.resource.ResourceResponse;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;
import net.branium.services.CourseService;
import net.branium.services.ResourceService;
import net.branium.services.UserService;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/dashboard")
@RequiredArgsConstructor
@Validated
public class DashboardController {
    private final UserService userService;
    private final CourseService courseService;

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

    @PostMapping(path = "/users/{id}/image")
    public ResponseEntity<ApiResponse<ResourceResponse>> uploadUserImage(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "image") MultipartFile file) {
        ResourceResponse resourceResponse = userService.updateUserImage(id, file);
        var response = ApiResponse.<ResourceResponse>builder()
                .message("upload user's image success")
                .result(resourceResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/users/{id}/image")
    public ResponseEntity<ApiResponse<ResourceResponse>> getUserImage(@PathVariable(name = "id") String id) {
        ResourceResponse resourceResponse = userService.getUserImage(id);
        var response = ApiResponse.<ResourceResponse>builder()
                .message("get user's image success")
                .result(resourceResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/courses")
    public ResponseEntity<?> createCourse(@RequestBody @Valid CourseCreateRequest request) {
        CourseResponse response = courseService.createCourse(request);
        return null;
    }
}
