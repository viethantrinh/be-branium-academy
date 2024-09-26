package net.branium.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.file.FileResponse;
import net.branium.dtos.user.*;
import net.branium.services.FileService;
import net.branium.services.UserService;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final FileService fileService;

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

    @PostMapping(path = "/avatar")
    public ResponseEntity<ApiResponse<FileResponse>> createUserAvatarById(@RequestParam(name = "file") MultipartFile multipartFile, Authentication authentication) {
        FileResponse file = fileService.uploadStudentAvatar(multipartFile, authentication);
        ApiResponse<FileResponse> response = ApiResponse.<FileResponse>builder()
                .message("upload user's avatar successful")
                .result(file)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/avatar")
    public ResponseEntity<ApiResponse<FileResponse>> getUserAvatarById(Authentication authentication) {
        FileResponse file = fileService.getStudentAvatar(authentication);
        ApiResponse<FileResponse> response = ApiResponse.<FileResponse>builder()
                .message("get user's avatar successful")
                .result(file)
                .build();
        return ResponseEntity.ok(response);
    }
}
