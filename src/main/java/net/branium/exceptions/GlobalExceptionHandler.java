package net.branium.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleGlobalException(HttpServletRequest request,
                                                               RuntimeException ex) {

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timeStamp(LocalDateTime.now())
                .path(request.getServletPath())
                .errors(List.of(Error.UNCATEGORIZED_ERROR.getMessage()))
                .build();
        log.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleApplicationException(HttpServletRequest request,
                                                                    ApplicationException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .status(ex.getError().getStatus().value())
                .timeStamp(LocalDateTime.now())
                .path(request.getServletPath())
                .errors(List.of(ex.getMessage()))
                .build();
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(response, ex.getError().getStatus());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(HttpServletRequest request,
                                                                     AuthorizationDeniedException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .timeStamp(LocalDateTime.now())
                .path(request.getServletPath())
                .errors(List.of(ex.getMessage()))
                .build();
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
