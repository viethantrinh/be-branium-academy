package net.branium.exceptions;


import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final Error error;

    public ApplicationException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}
