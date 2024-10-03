package net.branium.mappers;

import net.branium.domains.User;
import net.branium.dtos.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "wishList", ignore = true)
    @Mapping(target = "quizResults", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUser(UserCreateRequest request);

    @Mapping(target = "wishList", ignore = true)
    @Mapping(target = "quizResults", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    @Mapping(target = "wishList", ignore = true)
    @Mapping(target = "quizResults", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateUser(@MappingTarget User user, StudentUpdateRequest request);

    UserResponse toUserResponse(User user);
    StudentResponse toStudentResponse(User user);
}
