package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.domains.Role;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.repositories.RoleRepository;
import net.branium.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepo;

    @Override
    public Role create(Role role) {
        Role savedRole = roleRepo.save(role);
        return savedRole;
    }

    @Override
    public Role getByName(String name) {
        Role role = roleRepo.findById(name)
                .orElseThrow(() -> new ApplicationException(ErrorCode.ROLE_NON_EXISTED));
        return role;
    }

    @Override
    public List<Role> list() {
        List<Role> roles = roleRepo.findAll();
        return roles;
    }

    @Override
    public void deleteByName(String name) {
        Role role = roleRepo.findById(name)
                .orElseThrow(() -> new ApplicationException(ErrorCode.ROLE_NON_EXISTED));
        roleRepo.delete(role);
    }
}
