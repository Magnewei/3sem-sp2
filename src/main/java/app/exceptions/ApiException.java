package app.exceptions;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class ApiException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(ApiException.class);

    private final int statusCode;

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        writeToLog(message);
    }

    private void writeToLog(String message) {
        logger.error("Error occurred: Status Code: {}, Message: {}", statusCode, message);
    }
}
