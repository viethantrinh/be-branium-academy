package net.branium.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Error {
    UNCATEGORIZED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Uncategorized error"),
    USER_NON_EXISTED(HttpStatus.NOT_FOUND, "User not existed"),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "User not authenticated");

    private final HttpStatus status;
    private final String message;
}
