package chat.message;

/**
 * Exception class for waiting message answer fall.
 */
public class MessageTimeoutException extends RuntimeException {
    private static final long serialVersionUID = -4195594624242464037L;

    public MessageTimeoutException(String message) {
        super(message);
    }

}
