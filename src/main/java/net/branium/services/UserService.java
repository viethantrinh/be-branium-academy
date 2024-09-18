package net.branium.services;

import net.branium.dtos.user.*;

import java.util.List;


public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    UserResponse getUserById(String id);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(String id, UserUpdateRequest request);
    void deleteUserById(String id);
    StudentResponse getStudentInfo();
    StudentResponse updateStudentInfo(StudentUpdateRequest request);
}
