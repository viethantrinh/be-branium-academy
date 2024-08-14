package net.branium.mappers;


import net.branium.domains.Role;
import net.branium.dtos.role.RoleRequest;
import net.branium.dtos.role.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    Role toRole(RoleResponse roleResponse);
    Role toRole(RoleRequest roleRequest);
    RoleResponse toRoleResponse(Role role);

}
