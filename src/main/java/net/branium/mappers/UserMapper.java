package net.branium.mappers;

import net.branium.domains.User;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {Integer.class})
public interface UserMapper {

    @Mappings({
            @Mapping(target = "enabled", expression = "java(Boolean.TRUE)"),
            @Mapping(target = "vipLevel", expression = "java(0)"),
    })
    User toUser(UserCreateRequest userCreateRequest);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
