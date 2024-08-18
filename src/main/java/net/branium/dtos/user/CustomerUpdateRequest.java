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
public class CustomerUpdateRequest {
    private String username;
    //    private String password; // TODO: implement reset password later
    private String firstName;
    private String lastName;
    private boolean gender;
    private LocalDate birthDate;
    private String phoneNumber;
}
