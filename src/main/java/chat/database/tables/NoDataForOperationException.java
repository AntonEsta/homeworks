package chat.database.tables;

public class NoDataForOperationException extends Exception {
    private static final long serialVersionUID = 63400758208702832L;

    public NoDataForOperationException(String message) {
        super(message);
    }

}
