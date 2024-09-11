package net.branium.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {
    ROLE_ADMIN("ADMIN", "admin role"),
    ROLE_STUDENT("STUDENT", "student role");

    private final String name;
    private final String description;
}
