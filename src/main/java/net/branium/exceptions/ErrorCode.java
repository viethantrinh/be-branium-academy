package net.branium.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_ERROR(9999, HttpStatus.INTERNAL_SERVER_ERROR, "Uncategorized error"),
    USER_NON_EXISTED(1001, HttpStatus.NOT_FOUND, "User not existed"),
    USER_EXISTED(1002, HttpStatus.BAD_REQUEST, "User existed"),
    UNAUTHENTICATED(1003, HttpStatus.UNAUTHORIZED, "User not authenticated"),
    UNAUTHORIZED(1004, HttpStatus.FORBIDDEN, "User not have access"),
    ROLE_NON_EXISTED(1005, HttpStatus.NOT_FOUND, "Role not existed"),
    INVALID_FIELD(1006, HttpStatus.BAD_REQUEST, "JSON request body have some fields not valid"),
    INVALID_PARAM(1007, HttpStatus.BAD_REQUEST, "Path parameter or Query parameter is invalid"),
    INVALID_TOKEN(1008, HttpStatus.BAD_REQUEST, "Something wrong with the token"),
    RESOURCE_NON_EXISTED(1009, HttpStatus.NOT_FOUND, "Resource not existed");

    private final int code;
    private final HttpStatus status;
    private final String message;
}
