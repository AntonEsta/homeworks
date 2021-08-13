package chat.exception;

public class MessageFormatException extends RuntimeException {
    private static final long serialVersionUID = 1870512459773851112L;

    public MessageFormatException() {
        super();
    }

    public MessageFormatException(String message) {
        super(message);
    }
}
