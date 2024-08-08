package net.branium.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleGlobalException(HttpServletRequest request,
                                                               Exception ex) {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timeStamp(LocalDateTime.now())
                .path(request.getServletPath())
                .errors(List.of(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()))
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
}
