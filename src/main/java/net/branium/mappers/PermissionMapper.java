package net.branium.mappers;


import net.branium.domains.Permission;
import net.branium.dtos.permission.PermissionRequest;
import net.branium.dtos.permission.PermissionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionMapper {
    Permission toPermission(PermissionResponse permissionResponse);
    Permission toPermission(PermissionRequest permissionRequest);
    PermissionResponse toPermissionResponse(Permission permission);
}
