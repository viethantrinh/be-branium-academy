package net.branium.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean gender;
    private LocalDate birthDate;
    private String phoneNumber;
}

