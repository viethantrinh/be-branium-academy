package net.branium.services;

import net.branium.domains.Permission;
import net.branium.domains.Role;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IPermissionService {
    Permission create(Permission permission);

    Permission getByName(String name);

    List<Permission> list();

    void deleteByName(String name);

    List<Permission> listByRole(Role role);
}
