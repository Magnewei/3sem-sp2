package app.exceptions;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class JpaException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(JpaException.class);

    public JpaException(String message) {
        super(message);
        writeToLog(message);
    }

    private void writeToLog(String message) {
        logger.error("JPA Exception occurred: {}", message);
    }
}
