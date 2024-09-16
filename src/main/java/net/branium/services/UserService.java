package net.branium.services;

import net.branium.domains.User;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;

import java.util.List;


public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    UserResponse getUserById(String id);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(String id, UserUpdateRequest request);
    void deleteUser(String id);
}
