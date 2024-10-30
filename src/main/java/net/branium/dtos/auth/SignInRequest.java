package net.branium.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.branium.validation.ValidPassword;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    @NotNull(message = "Email must no be null")
    @Length(min = 2, max = 50, message = "Email must be between 5 and 128 characters")
    @Email
    private String email;

    @NotNull(message = "Password must no be null")
    @Length(min = 8, message = "Password must be at least 8 characters")
    @ValidPassword
    private String password;
}
