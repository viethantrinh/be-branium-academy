package net.branium.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.branium.domains.Role;
import net.branium.dtos.auth.RoleResponse;

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
    private Boolean gender;
    private LocalDate birthDate;
    private Integer vipLevel;
    private String phoneNumber;
    private Set<RoleResponse> roles = new HashSet<>();
}
