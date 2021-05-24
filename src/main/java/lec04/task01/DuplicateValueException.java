package lec04.task01;

public class DuplicateValueException extends Exception {

    private static final String MESSAGE = "The value already exists";

    public DuplicateValueException() {
        super(MESSAGE);
    }

    public DuplicateValueException(String message){
        super(message);
    }
}
