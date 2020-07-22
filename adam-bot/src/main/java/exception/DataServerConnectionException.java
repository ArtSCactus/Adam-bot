package exception;

/**
 * @author ArtSCactus
 * @version 1.0
 */
public class DataServerConnectionException extends RuntimeException {
    public DataServerConnectionException() {
    }

    public DataServerConnectionException(String message) {
        super(message);
    }

    public DataServerConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataServerConnectionException(Throwable cause) {
        super(cause);
    }

    public DataServerConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
