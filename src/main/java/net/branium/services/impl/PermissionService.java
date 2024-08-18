package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.domains.Permission;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.repositories.PermissionRepository;
import net.branium.services.IPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {

    private final PermissionRepository permissionRepo;

    @Override
    public Permission create(Permission permission) {
        Permission savedPermission = permissionRepo.save(permission);
        return savedPermission;
    }

    @Override
    public Permission getByName(String name) {
        Permission permission = permissionRepo.findById(name)
                .orElseThrow(() -> new ApplicationException(Error.PERMISSION_NON_EXISTED));
        return permission;
    }

    @Override
    public List<Permission> list() {
        List<Permission> permissions = permissionRepo.findAll();
        return permissions;
    }

    @Override
    public void deleteByName(String name) {
        Permission permission = permissionRepo.findById(name)
                .orElseThrow(() -> new ApplicationException(Error.PERMISSION_NON_EXISTED));
        permissionRepo.delete(permission);
    }

}
