package net.branium.dtos.user;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.branium.dtos.role.RoleRequest;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private boolean gender;
    private LocalDate birthDate;
    private int vipLevel;
    private String phoneNumber;
    private Set<RoleRequest> roles = new HashSet<>();
}
