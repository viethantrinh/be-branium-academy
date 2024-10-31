package net.branium.dtos.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateRequest {
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

    private Boolean gender;

    @Past(message = "Date of birth must be a past date")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^\\d{10,12}$", message = "Phone number must contain 10-12 digits only")
    private String phoneNumber;
}
