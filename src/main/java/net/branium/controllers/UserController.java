package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.User;
import net.branium.dtos.user.CustomerUpdateRequest;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;
import net.branium.mappers.UserMapper;
import net.branium.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/customer-info")
    public ResponseEntity<?> getCustomerInfo() {
        User user = userService.getCustomerInfo();
        UserResponse response = userMapper.toUserResponse(user);
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
