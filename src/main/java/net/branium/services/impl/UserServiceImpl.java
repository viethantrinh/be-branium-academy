package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.User;
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
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepo.save(user);
        return savedUser;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public User getById(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));
        return user;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<User> list() {
        List<User> users = userRepo.findAll();
        return users;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public User update(String id, User user) {
        User updateUser = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));
        userMapper.updateUser(updateUser, user);
        updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        User updatedUser = userRepo.save(updateUser);
        return updatedUser;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void delete(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));
        userRepo.delete(user);
    }
}
