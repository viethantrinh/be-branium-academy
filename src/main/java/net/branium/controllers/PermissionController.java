package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.Permission;
import net.branium.dtos.permission.PermissionRequest;
import net.branium.dtos.permission.PermissionResponse;
import net.branium.mappers.PermissionMapper;
import net.branium.services.IPermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionMapper permissionMapper;
    private final IPermissionService permissionService;

    @PostMapping
    public ResponseEntity<?> createPermission(@RequestBody PermissionRequest request) {
        Permission createdPermission = permissionService.create(permissionMapper.toPermission(request));
        PermissionResponse response = permissionMapper.toPermissionResponse(createdPermission);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{name}")
    public ResponseEntity<?> getPermission(@PathVariable(value = "name") String name) {
        Permission Permission = permissionService.getByName(name);
        PermissionResponse response = permissionMapper.toPermissionResponse(Permission);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getPermissions() {
        List<Permission> Permissions = permissionService.list();
        List<PermissionResponse> response = Permissions.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deletePermission(@PathVariable(value = "name") String name) {
        permissionService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }
}
