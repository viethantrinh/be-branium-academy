package net.branium.services;

import net.branium.domains.Role;

import java.util.List;

public interface RoleService {
    Role create(Role role);

    Role getByName(String name);

    List<Role> list();

    void deleteByName(String name);
}
