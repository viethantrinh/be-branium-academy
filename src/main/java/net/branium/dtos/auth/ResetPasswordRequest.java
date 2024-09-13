package net.branium.dtos.auth;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    @NotNull(message = "email field must not be null")
    @Email(message = "email must have valid format")
    private String email;

    @NotNull(message = "password field must not be null")
    @Length(min = 8, message = "password must have at least 8 characters")
    private String newPassword;

    @Length(min = 6, max = 6, message = "The code is invalid! Must be only 6 characters!")
    @NotNull(message = "code field must not be null")
    private String code;
}
