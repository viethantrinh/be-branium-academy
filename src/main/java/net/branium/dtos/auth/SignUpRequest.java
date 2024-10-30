package net.branium.dtos.auth;

import jakarta.validation.constraints.*;
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
public class SignUpRequest {

    @NotNull(message = "First name must no be null")
    @Length(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern.List({
            @Pattern(regexp = "^[^0-9]*$", message = "First name must not have number"),
            @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "First name must not have special characters")
    })
    private String firstName;

    @NotNull(message = "Last name must no be null")
    @Length(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern.List({
            @Pattern(regexp = "^[^0-9]*$", message = "Last name must not have number"),
            @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "First name must not have special characters")
    })
    private String lastName;

    @NotNull(message = "Email must no be null")
    @Length(min = 2, max = 50, message = "Email must be between 5 and 128 characters")
    @Email
    private String email;

    @NotNull(message = "Password must no be null")
    @Length(min = 8, message = "Password must be at least 8 characters")
    @ValidPassword
    private String password;
}
