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
    private final PasswordEncoder passwordEncoder;

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
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = userRepo.save(user);
        UserResponse response = userMapper.toUserResponse(savedUser);
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserResponse getUserById(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));
        UserResponse response = userMapper.toUserResponse(user);
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));

        // mapped the update field to the entity
        userMapper.updateUser(user, request);

        if (request.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            user.setPassword(encodedPassword);
        }

        User savedUser = userRepo.save(user);
        UserResponse response = userMapper.toUserResponse(savedUser);
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteUserById(String id) {
        // check if the user's email is existed or not
        if (!userRepo.existsById(id)) {
            throw new ApplicationException(ErrorCode.USER_NON_EXISTED);
        }

        // delete the user
        userRepo.deleteById(id);
    }
}
