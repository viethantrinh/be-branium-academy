package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.User;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.mappers.UserMapper;
import net.branium.repositories.UserRepository;
import net.branium.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final UserMapper userMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse createUser(UserCreateRequest request) {
        // check if the user's email is existed or not
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new ApplicationException(ErrorCode.USER_EXISTED);
        }

        // map the creating user request to user entity
        User user = userMapper.toUser(request);

        // save the user to db
        User savedUser = userRepo.save(user);
        UserResponse response = userMapper.toUserResponse(savedUser);
        return response;
    }

    @Override
    public UserResponse getUserById(String id) {
        return null;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return List.of();
    }

    @Override
    public UserResponse updateUser(String id, UserUpdateRequest request) {
        return null;
    }

    @Override
    public void deleteUser(String id) {

    }
}
