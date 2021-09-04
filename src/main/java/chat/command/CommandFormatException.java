package chat.command;

/**
 * Exception class for wrong command format.
 */
public class CommandFormatException extends RuntimeException {
    private static final long serialVersionUID = 6181803816376908774L;

    public CommandFormatException() {
        super();
    }

    public CommandFormatException(String message) {
        super(message);
    }

    public CommandFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandFormatException(Throwable cause) {
        super(cause);
    }
}
