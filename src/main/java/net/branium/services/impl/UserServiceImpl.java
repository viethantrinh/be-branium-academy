package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.ResourceType;
import net.branium.domains.User;
import net.branium.dtos.resource.ResourceResponse;
import net.branium.dtos.user.*;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.mappers.UserMapper;
import net.branium.repositories.UserRepository;
import net.branium.services.ResourceService;
import net.branium.services.UserService;
import net.branium.utils.ResourceUtils;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ResourceService resourceService;

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

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResourceResponse updateUserImage(String id, MultipartFile file) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));
        String fileCode = resourceService.uploadUserImage(user, file);
        return ResourceResponse.builder()
                .url(ResourceUtils.buildDownloadUrl(fileCode, ResourceType.IMAGE))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResourceResponse getUserImage(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));
        String image = user.getImage();

        if (image == null) {
            throw new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED);
        }

        String fileCode = image.substring(0, 8);

        return ResourceResponse.builder()
                .url(ResourceUtils.buildDownloadUrl(fileCode, ResourceType.IMAGE))
                .build();
    }

    @PostAuthorize("authentication.name.equals(returnObject.email)")
    @Override
    public StudentResponse getStudentInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED);
        }
        String email = authentication.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));
        StudentResponse response = userMapper.toStudentResponse(user);
        return response;
    }

    @PostAuthorize("authentication.name.equals(returnObject.email)")
    @Override
    public StudentResponse updateStudentInfo(StudentUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED);
        }
        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));

        // mapped the update field to the entity
        userMapper.updateUser(user, request);

        User savedUser = userRepo.save(user);
        StudentResponse response = userMapper.toStudentResponse(savedUser);
        return response;
    }

    @Override
    public ResourceResponse updateStudentImage(MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));
        String fileCode = resourceService.uploadUserImage(user, file);
        return ResourceResponse.builder()
                .url(ResourceUtils.buildDownloadUrl(fileCode, ResourceType.IMAGE))
                .build();
    }

    @Override
    public ResourceResponse getStudentImage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));
        String image = user.getImage();

        if (image == null) {
            throw new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED);
        }

        String fileCode = image.substring(0, 8);

        return ResourceResponse.builder()
                .url(ResourceUtils.buildDownloadUrl(fileCode, ResourceType.IMAGE))
                .build();
    }

}
