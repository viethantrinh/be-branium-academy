package net.branium.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @NotNull(message = "firstName field must not be null")
    @NotEmpty(message = "firstName must not be blank or empty")
    private String firstName;

    @NotNull(message = "lastName field must not be null")
    @NotEmpty(message = "lastName must not be blank or empty")
    private String lastName;

    @NotNull(message = "email field must not be null")
    @NotEmpty(message = "email must not be blank or empty")
    @Email(message = "email must have valid format")
    private String email;

    @NotNull(message = "password field must not be null")
    @NotEmpty(message = "password must not be blank or empty")
    @Length(min = 8, message = "password must have at least 8 characters")
    private String password;
}
