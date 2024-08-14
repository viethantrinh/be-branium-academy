package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.Role;
import net.branium.dtos.role.RoleRequest;
import net.branium.dtos.role.RoleResponse;
import net.branium.mappers.RoleMapper;
import net.branium.services.IRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleMapper roleMapper;
    private final IRoleService roleService;

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleRequest request) {
        Role createdRole = roleService.create(roleMapper.toRole(request));
        RoleResponse response = roleMapper.toRoleResponse(createdRole);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{name}")
    public ResponseEntity<?> getRole(@PathVariable(value = "name") String name) {
        Role role = roleService.getByName(name);
        RoleResponse response = roleMapper.toRoleResponse(role);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getRoles() {
        List<Role> roles = roleService.list();
        List<RoleResponse> response = roles.stream()
                .map(roleMapper::toRoleResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteRole(@PathVariable(value = "name") String name) {
        roleService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }

}
