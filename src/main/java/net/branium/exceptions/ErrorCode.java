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
    RESOURCE_NON_EXISTED(1009, HttpStatus.NOT_FOUND, "Resource not existed"),
    USER_NOT_ACTIVATED(1010, HttpStatus.FORBIDDEN, "User is not activated"),
    REQUEST_METHOD_NOT_SUPPORT(1011, HttpStatus.METHOD_NOT_ALLOWED, "Request method is not supported"),
    OVERSIZE_FILE(1012, HttpStatus.PAYLOAD_TOO_LARGE, "oversize file"),
    INVALID_IMAGE_EXTENSION(1013, HttpStatus.METHOD_NOT_ALLOWED, "image extension is not valid"),
    BAD_REQUEST(9998, HttpStatus.BAD_REQUEST, "bad request"),
    COURSE_NON_EXISTED(1014, HttpStatus.NOT_FOUND, "Course not existed"),
    COURSE_ALREADY_BOUGHT(1015, HttpStatus.CONFLICT, "Course already bought"),
    COURSE_ALREADY_IN_CART(1016, HttpStatus.CONFLICT, "This course already in your cart"),
    COURSE_ALREADY_IN_WISHLIST(1017, HttpStatus.CONFLICT, "Course is already in wishlist"),
    ORDER_NOT_EXISTED(1018, HttpStatus.NOT_FOUND, "Order is not existed"),
    ORDER_STATUS_NOT_PROCESSING(1019, HttpStatus.CONFLICT, "Order's status is not processing");

    private final int code;
    private final HttpStatus status;
    private final String message;
}
