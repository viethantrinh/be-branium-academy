package net.branium.services;

import net.branium.dtos.resource.ResourceResponse;
import net.branium.dtos.user.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    UserResponse getUserById(String id);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(String id, UserUpdateRequest request);
    void deleteUserById(String id);
    ResourceResponse updateUserImage(String id, MultipartFile file);
    ResourceResponse getUserImage(String id);
    StudentResponse getStudentInfo();
    StudentResponse updateStudentInfo(StudentUpdateRequest request);
    ResourceResponse updateStudentImage(MultipartFile file);
    ResourceResponse getStudentImage();
}
