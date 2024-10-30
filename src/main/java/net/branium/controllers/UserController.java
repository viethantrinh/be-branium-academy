package net.branium.controllers;

import com.google.common.io.Files;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.resource.ResourceResponse;
import net.branium.dtos.user.StudentResponse;
import net.branium.dtos.user.StudentUpdateRequest;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    private static final String[] SUPPORTED_IMAGE_TYPES = {"jpeg", "png"};

    @GetMapping(path = "/info")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentInfo() {
        StudentResponse response = userService.getStudentInfo();
        var responseBody = ApiResponse.<StudentResponse>builder()
                .message("get student info successful")
                .result(response)
                .build();
        return ResponseEntity.ok(responseBody);
    }

    @PutMapping(path = "/info")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudentInfo(
            @RequestBody @Valid StudentUpdateRequest request) {
        StudentResponse response = userService.updateStudentInfo(request);
        var responseBody = ApiResponse.<StudentResponse>builder()
                .message("update student info successful")
                .result(response)
                .build();
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping(path = "/image")
    public ResponseEntity<ApiResponse<ResourceResponse>> uploadStudentImage(
            @RequestParam(name = "image") MultipartFile file) {


        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ApplicationException(ErrorCode.OVERSIZE_FILE);
        }

        boolean isValidType = false;
        for (String type : SUPPORTED_IMAGE_TYPES) {
            if (Files.getFileExtension(Objects.requireNonNull(file.getOriginalFilename())).equalsIgnoreCase(type)) {
                isValidType = true;
                break;
            }
        }

        if (!isValidType) {
            throw new ApplicationException(ErrorCode.INVALID_IMAGE_EXTENSION);
        }

        ResourceResponse resourceResponse = userService.updateStudentImage(file);

        var response = ApiResponse.<ResourceResponse>builder()
                .message("upload student's image success")
                .result(resourceResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/image")
    public ResponseEntity<ApiResponse<ResourceResponse>> getStudentImage() {
        ResourceResponse resourceResponse = userService.getStudentImage();
        var response = ApiResponse.<ResourceResponse>builder()
                .message("get student's image success")
                .result(resourceResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
