package net.branium.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.branium.dtos.role.RoleRequest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private boolean gender;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private Set<RoleRequest> roles = new HashSet<>();
}
