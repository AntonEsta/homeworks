package petscardfile.classes.exceptions;

public class DuplicateValueException extends Exception {

    private static final String MESSAGE = "The value already exists";

    public DuplicateValueException() {
        super(MESSAGE);
    }

}
