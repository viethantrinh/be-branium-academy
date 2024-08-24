package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.User;
import net.branium.dtos.user.*;
import net.branium.mappers.UserMapper;
import net.branium.services.IUserService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final UserMapper userMapper;

    /**
     * Create user api, only admin can use this to create user
     *
     * @param request the request body which contain the user's data
     * @return the user data if created successful
     */
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

    @GetMapping(path = "/{id}/avatar")
    public ResponseEntity<?> getUserAvatar(@PathVariable(value = "id") String id) {
        User user = userService.getById(id);
        UserAvatar response = UserAvatar.builder().email(user.getEmail()).avatar(user.getAvatar()).build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") String id,
                                        @RequestBody UserUpdateRequest request) {
        User userUpdateRequest = userMapper.toUser(request);
        User updatedUser = userService.update(id, userUpdateRequest);
        UserResponse userResponse = userMapper.toUserResponse(updatedUser);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping(path = "/{id}/avatar")
    public ResponseEntity<?> updateUserAvatar(@PathVariable(value = "id") String id,
                                              @RequestParam(value = "avatar") MultipartFile avatarFile) {
        userService.updateUserAvatar(id, avatarFile);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/customer-info")
    public ResponseEntity<?> getCustomerInfo() {
        User user = userService.getCustomerInfo();
        UserResponse response = userMapper.toUserResponse(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/customer-info/avatar")
    public ResponseEntity<?> getCustomerAvatar() {
        User user = userService.getCustomerInfo();
        UserAvatar response = UserAvatar.builder().email(user.getEmail()).avatar(user.getAvatar()).build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/customer-info/{id}")
    public ResponseEntity<?> updateCustomerInfo(@PathVariable(value = "id") String id,
                                                @RequestBody CustomerUpdateRequest request) {
        User userUpdateRequest = userMapper.toUser(request);
        User updatedUser = userService.updateCustomer(id, userUpdateRequest);
        UserResponse userResponse = userMapper.toUserResponse(updatedUser);
        return ResponseEntity.ok(userResponse);
    }
}
