package net.branium.mappers;

import net.branium.domains.User;
import net.branium.dtos.auth.SignInRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {
    User toUser(SignInRequest signInRequest);
}
