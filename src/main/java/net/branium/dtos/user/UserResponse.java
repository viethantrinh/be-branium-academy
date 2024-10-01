package net.branium.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.branium.dtos.role.RoleResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private boolean gender;
    private LocalDate birthDate;
    private String avatar;
    private String phoneNumber;
    private Set<RoleResponse> roles = new HashSet<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
