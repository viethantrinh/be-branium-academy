package net.branium.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.branium.dtos.role.RoleResponse;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean gender;
    private boolean enabled;
    private LocalDate birthDate;
    private int vipLevel;
    private String phoneNumber;
    private Set<RoleResponse> roles = new HashSet<>();
}
