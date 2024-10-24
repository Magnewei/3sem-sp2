package dat.exceptions;

import lombok.Getter;

public class ApiException extends Exception {
    @Getter
    private final int statusCode;

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
