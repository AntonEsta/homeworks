package chat.message;

/**
 * Exception class for wrong message format.
 */
public class MessageFormatException extends RuntimeException {
    private static final long serialVersionUID = 1870512459773851112L;

    public MessageFormatException(String message) {
        super(message);
    }
}
