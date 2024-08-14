package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.User;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.mappers.UserMapper;
import net.branium.repositories.UserRepository;
import net.branium.services.IUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        User savedUser = userRepo.save(user);
        return savedUser;
    }

    @Override
    public User signUp(User user) {
        User registeredUser = userRepo.save(user);
        return registeredUser;
    }

    @Override
    public User getById(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
        return user;
    }

    @Override
    public User getByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
        return user;
    }

    @Override
    public List<User> list() {
        List<User> users = userRepo.findAll();
        return users;
    }

    @Override
    public User update(String id, User user) {
        User updateUser = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
//        userMapper.updateUser(updateUser, user);

        updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        User updatedUser = userRepo.save(updateUser);
        return updatedUser;
    }

    @Override
    public void delete(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
        userRepo.delete(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }
}
