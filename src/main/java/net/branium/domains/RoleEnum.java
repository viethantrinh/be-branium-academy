package net.branium.domains;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {
    ROLE_ADMIN("ADMIN"),
    ROLE_STUDENT("STUDENT");

    private final String name;
}
