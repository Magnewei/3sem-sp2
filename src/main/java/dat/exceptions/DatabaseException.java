package dat.exceptions;

import lombok.Getter;

@Getter
public class DatabaseException extends RuntimeException {
    private final String errorMessage;
    private final int statusCode;

    public DatabaseException(int statusCode, String message, Throwable errorMessage) {
        super(message + ": " + errorMessage.getMessage());
        this.statusCode = statusCode;
        this.errorMessage = String.valueOf(errorMessage);
    }
}
