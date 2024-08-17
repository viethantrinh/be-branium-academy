package net.branium.mappers;

import net.branium.domains.User;
import net.branium.dtos.user.CustomerUpdateRequest;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "avatar", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    User toUser(UserCreateRequest userCreateRequest);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "avatar", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    User toUser(UserUpdateRequest userUpdateRequest);

    @Mappings({
            @Mapping(target = "vipLevel", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "enabled", ignore = true),
            @Mapping(target = "email", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "avatar", ignore = true)
    })
    User toUser(CustomerUpdateRequest customerUpdateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUser(@MappingTarget User updateUser, User user);


    UserResponse toUserResponse(User user);
}
