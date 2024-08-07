package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.User;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.mappers.UserMapper;
import net.branium.repositories.UserRepository;
import net.branium.services.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserCreateRequest request) {
        User user = userMapper.toUser(request);
        log.info("Created user: {}", user);
        User savedUser = userRepo.save(user);
        UserResponse response = userMapper.toUserResponse(savedUser);
        return response;
    }

    @Override
    public UserResponse getById(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
        UserResponse response = userMapper.toUserResponse(user);
        return response;
    }

    @Override
    public List<UserResponse> list() {
        List<User> users = userRepo.findAll();
        List<UserResponse> responseList = users
                .stream()
                .map(userMapper::toUserResponse).collect(Collectors.toList());
        return responseList;

    }

    @Override
    public UserResponse update(String id, UserUpdateRequest request) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserResponse response = userMapper.toUserResponse(userRepo.save(user));
        return response;
    }

    @Override
    public void delete(String id) {
        userRepo.deleteById(id);
    }
}
