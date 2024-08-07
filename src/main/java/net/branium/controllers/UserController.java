package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;
import net.branium.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequest request) {
        UserResponse response = userService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<?> getUser(@PathVariable(value = "userId") String userId) {
        UserResponse response = userService.getById(userId);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<UserResponse> responseList = userService.list();
        return !responseList.isEmpty()
                ? ResponseEntity.ok(responseList)
                : ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "userId") String id,
                                        @RequestBody UserUpdateRequest request) {
        UserResponse response = userService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "userId") String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
