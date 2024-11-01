package net.branium.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleGlobalException(HttpServletRequest request,
                                                               Exception ex) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.UNCATEGORIZED_ERROR.getCode())
                .message(ErrorCode.UNCATEGORIZED_ERROR.getMessage())
                .timeStamp(LocalDateTime.now())
                .path(request.getServletPath())
                .errors(List.of(ErrorCode.UNCATEGORIZED_ERROR.getMessage()))
                .build();
        log.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(HttpServletRequest request,
                                                                               MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> errors.add(fieldError.getDefaultMessage()));
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.INVALID_FIELD.getCode())
                .message(ErrorCode.INVALID_FIELD.getMessage())
                .timeStamp(LocalDateTime.now())
                .path(request.getServletPath())
                .errors(errors)
                .build();
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(HttpServletRequest request,
                                                                            ConstraintViolationException e) {
        Set<String> errors = new HashSet<>();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        constraintViolations.forEach((cv) -> errors.add(cv.getMessage()));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.INVALID_PARAM.getCode())
                .message(ErrorCode.INVALID_PARAM.getMessage())
                .timeStamp(LocalDateTime.now())
                .path(request.getServletPath())
                .errors(errors.stream().toList())
                .build();

        return ResponseEntity
                .status(ErrorCode.INVALID_PARAM.getStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleApplicationException(HttpServletRequest request,
                                                                    ApplicationException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .code(ex.getErrorCode().getCode())
                .message(ex.getErrorCode().getMessage())
                .timeStamp(LocalDateTime.now())
                .path(request.getServletPath())
                .errors(List.of(ex.getMessage()))
                .build();
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(response, ex.getErrorCode().getStatus());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(HttpServletRequest request,
                                                                     AuthorizationDeniedException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.UNAUTHORIZED.getCode())
                .message(ErrorCode.UNAUTHORIZED.getMessage())
                .timeStamp(LocalDateTime.now())
                .path(request.getServletPath())
                .errors(List.of(ex.getMessage()))
                .build();
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AsyncRequestNotUsableException.class)
    void handleAsyncRequestNotUsableException(AsyncRequestNotUsableException e) {
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpServletRequest request,
                                                                  HttpRequestMethodNotSupportedException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getCode())
                .message(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getMessage())
                .timeStamp(LocalDateTime.now())
                .path(request.getServletPath())
                .errors(List.of(ex.getMessage()))
                .build();
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMissingParameter(HttpServletRequest request,
                                                                MissingServletRequestPartException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.BAD_REQUEST.getCode())
                .message(ErrorCode.BAD_REQUEST.getMessage())
                .timeStamp(LocalDateTime.now())
                .path(request.getServletPath())
                .errors(List.of(ex.getMessage()))
                .build();
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMissingParameter(HttpServletRequest request,
                                                                HttpMessageNotReadableException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.INVALID_FIELD.getCode())
                .message(ErrorCode.INVALID_FIELD.getMessage())
                .timeStamp(LocalDateTime.now())
                .path(request.getServletPath())
                .errors(List.of(ex.getMessage()))
                .build();
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
