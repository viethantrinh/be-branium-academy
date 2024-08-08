package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.constants.AuthorityConstants;
import net.branium.domains.Role;
import net.branium.domains.User;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.mappers.UserMapper;
import net.branium.repositories.RoleRepository;
import net.branium.repositories.UserRepository;
import net.branium.services.IUserService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserCreateRequest request) {
        User user = userMapper.toUser(request);
        log.info("Created user: {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role customerRole = roleRepo.findByName(AuthorityConstants.ROLE_CUSTOMER)
                .orElseThrow(() -> new ApplicationException(Error.ROLE_NON_EXISTED));
        user.setRoles(Set.of(customerRole));
        User savedUser = userRepo.save(user);
        UserResponse response = userMapper.toUserResponse(savedUser);
        return response;
    }

    @PostAuthorize("returnObject.email.equals(authentication.name)")
    @Override
    public UserResponse getById(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
        UserResponse response = userMapper.toUserResponse(user);
        return response;
    }

    @Override
    public UserResponse getByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
        UserResponse response = userMapper.toUserResponse(user);
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserResponse> list() {
        List<User> users = userRepo.findAll();
        List<UserResponse> responseList = users
                .stream()
                .map(userMapper::toUserResponse).collect(Collectors.toList());
        return responseList;

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserResponse update(String id, UserUpdateRequest request) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserResponse response = userMapper.toUserResponse(userRepo.save(user));
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void delete(String id) {
        userRepo.deleteById(id);
    }
}
