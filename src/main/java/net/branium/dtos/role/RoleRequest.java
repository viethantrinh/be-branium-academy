package net.branium.dtos.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.branium.dtos.permission.PermissionRequest;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    private String name;
    private String description;
    private Set<PermissionRequest> permissions = new HashSet<>();
}
