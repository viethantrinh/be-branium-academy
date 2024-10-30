package net.branium.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        // Define regex patterns for each rule
        String digitRegex = ".*\\d.*"; // Contains at least one digit
        String specialCharRegex = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"|,.<>/?].*"; // Contains at least one special character
        String upperCaseRegex = ".*[A-Z].*"; // Contains at least one uppercase letter

        // Check each rule and customize the error message
        if (!password.matches(digitRegex)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must contains at least 1 number")
                    .addConstraintViolation();
            return false;
        }

        if (!password.matches(specialCharRegex)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must contains at least 1 special character")
                    .addConstraintViolation();
            return false;
        }

        if (!password.matches(upperCaseRegex)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must contains at least 1 uppercase character")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
