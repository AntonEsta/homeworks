package chat.exception;

/**
 * Exception class for operation fall.
 */
public class OperationException extends RuntimeException {
    private static final long serialVersionUID = -7626587550493953410L;

    public OperationException(String message) {
        super(message);
    }

    public OperationException(String message, Throwable cause) {
        super(message, cause);
    }

}
