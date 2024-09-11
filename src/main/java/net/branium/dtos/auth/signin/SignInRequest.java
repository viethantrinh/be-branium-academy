package net.branium.dtos.auth.signin;

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
public class SignInRequest {
    @NotNull(message = "email field must not be null")
    @NotEmpty(message = "email must not be blank or empty")
    @Email(message = "email must have valid format")
    private String email;

    @NotNull(message = "password field must not be null")
    @NotEmpty(message = "password must not be blank or empty")
    @Length(min = 8, message = "password must have at least 8 characters")
    private String password;
}
