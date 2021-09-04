package chat.messanger;

/**
 * Exception thrown when no confirmation of message delivery.
 */
public class MessageConfirmationException extends RuntimeException {
    private static final long serialVersionUID = 4526278818470885104L;

    public MessageConfirmationException(String message) {
        super(message);
    }

    public MessageConfirmationException(String message, Throwable cause) {
        super(message, cause);
    }
}
