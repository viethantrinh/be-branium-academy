package net.branium.services;

import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;

import java.util.List;


public interface IUserService {
    UserResponse create(UserCreateRequest request);
    UserResponse getById(String id);
    UserResponse getByEmail(String email);
    List<UserResponse> list();
    UserResponse update(String id, UserUpdateRequest request);
    void delete(String id);

}
