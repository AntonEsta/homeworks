package chat.user;

/**
 * Exception class of wrong user.
 */
public class UserException extends RuntimeException {
    private static final long serialVersionUID = 539989700165700810L;

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
