package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.User;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.mappers.UserMapper;
import net.branium.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequest request) {
        User userCreateRequest = userMapper.toUser(request);
        User savedUser = userService.create(userCreateRequest);
        UserResponse response = userMapper.toUserResponse(savedUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(value = "id") String id) {
        User user = userService.getById(id);
        UserResponse response = userMapper.toUserResponse(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<User> users = userService.list();
        List<UserResponse> userResponses = users.stream()
                .map(userMapper::toUserResponse).toList();
        return !userResponses.isEmpty()
                ? ResponseEntity.ok(userResponses)
                : ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/myInfo")
    public ResponseEntity<UserResponse> getCustomerInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ApplicationException(Error.UNCATEGORIZED_ERROR);
        }
        User user = userService.getByEmail(authentication.getName());
        UserResponse response = userMapper.toUserResponse(user);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/myInfo/{email}")
    @PreAuthorize("#email.equals(authentication.name)")
    public ResponseEntity<UserResponse> updateCustomerInfo(@PathVariable(name = "email") String email, @RequestBody) {
        User user = userService.getByEmail(authentication.getName());
        UserResponse response = userMapper.toUserResponse(user);
        return ResponseEntity.ok(response);
    }




    // TODO: implement get user avatar
    @GetMapping(path = "/{id}/avatar")
    public ResponseEntity<MultipartFile> getUserAvatar(@PathVariable(value = "id") String id) {
        User user = userService.getById(id);
        return null;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") String id,
                                        @RequestBody UserUpdateRequest request) {
        User userUpdateRequest = userMapper.toUser(request);
        User updatedUser = userService.update(id, userUpdateRequest);
        UserResponse userResponse = userMapper.toUserResponse(updatedUser);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
