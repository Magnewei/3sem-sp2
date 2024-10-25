package dat.exceptions;

import lombok.Getter;

@Getter
public class DatabaseException extends RuntimeException {
    private String errorMessage;
    private final int statusCode;

    public DatabaseException(int statusCode, String message, String errorMessage) {
        super(message);
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public DatabaseException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
