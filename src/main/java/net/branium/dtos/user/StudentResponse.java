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
public class StudentResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean gender;
    private LocalDate dateOfBirth;
    private String image;
    private String phoneNumber;
    private Set<RoleResponse> roles = new HashSet<>();
}
