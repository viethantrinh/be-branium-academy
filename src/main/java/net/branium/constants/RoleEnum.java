package net.branium.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {
    ROLE_ADMIN("ADMIN", "admin role"),
    ROLE_CUSTOMER("CUSTOMER", "customer role"),
    ROLE_STUDENT("STUDENT", "student role"),
    ROLE_INSTRUCTOR("INSTRUCTOR", "instructor role");

    private final String name;
    private final String description;
}
