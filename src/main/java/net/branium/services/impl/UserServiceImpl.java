package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.User;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.mappers.UserMapperImpl;
import net.branium.repositories.UserRepository;
import net.branium.services.IUserService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapperImpl userMapper;

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
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
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
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
        userMapper.updateUser(updateUser, user);
        updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        User updatedUser = userRepo.save(updateUser);
        return updatedUser;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void updateUserAvatar(String id, MultipartFile avatar) {
        byte[] avatarFile = null;
        try {
            avatarFile = avatar.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
        user.setAvatar(avatarFile);
        userRepo.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void delete(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
        userRepo.delete(user);
    }

    @Override
    @PostAuthorize("returnObject.email == authentication.name")
    public User getCustomerInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ApplicationException(Error.UNAUTHENTICATED);
        }
        User user = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new ApplicationException(Error.USER_EXISTED));
        return user;
    }


    @Override
    @PostAuthorize("returnObject.email == authentication.name")
    public User updateCustomer(String id, User customer) {
        User updateUser = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
        userMapper.updateUser(updateUser, customer);
        User updatedUser = userRepo.save(updateUser);
        return updatedUser;
    }
}
