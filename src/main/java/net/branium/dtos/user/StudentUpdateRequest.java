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
public class StudentUpdateRequest {
    private String firstName;
    private String lastName;
    private boolean gender;
    private LocalDate birthDate;
    private String phoneNumber;
}
