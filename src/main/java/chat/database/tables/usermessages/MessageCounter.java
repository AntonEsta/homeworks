package chat.database.tables.usermessages;

import chat.database.tables.NoDataForOperationException;
import chat.exception.OperationException;
import lombok.NonNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for counting the number of messages not read yet.
 */
class MessageCounter {

    private final Map<@NonNull UUID, @NonNull Integer> table = new ConcurrentHashMap<>();

    /**
     * Check for the existence of a record for the specified message.
     * @param id message ID.
     * @throws NoDataForOperationException thrown when no data for the message.
     */
    private void requestExistMessage(@NonNull UUID id) throws NoDataForOperationException {
        if (!this.table.containsKey(id)) throw new NoDataForOperationException("Record not found.");
    }

    /**
     * Adds the new message.
     * @param id message ID.
     */
    public void addMessage(@NonNull UUID id) {
        try {
            incrementCount(id);
        } catch (NoDataForOperationException e) {
            this.table.put(id, 1);
        }
    }

    /**
     * Deletes the message.
     * @param id message ID.
     */
    public void deleteMessage(@NonNull UUID id) {
        try {
            decrementCount(id);
        } catch (NoDataForOperationException | OperationException e) {
            this.table.remove(id);
        }
    }

    /**
     * Increments count for the message.
     * @param id message ID.
     * @throws NoDataForOperationException thrown when no data for the message.
     */
    private void incrementCount(@NonNull UUID id) throws NoDataForOperationException {
        requestExistMessage(id);
        int count = this.table.get(id);
        this.table.put(id,++count);
    }

    /**
     * Decrements count for the message.
     * @param id message ID.
     * @throws NoDataForOperationException thrown when no data for the message.
     * @throws OperationException thrown when count equal zero.
     */
    private void decrementCount(@NonNull UUID id) throws NoDataForOperationException, OperationException {
        requestExistMessage(id);
        int count = this.table.get(id);
        if (count > 1) {
            this.table.put(id,--count);
        } else {
            throw new OperationException("Count equal zero.");
        }
    }

    /**
     * Returns count of message.
     * @param id message ID.
     * @return Message count.
     */
    public int getCount(@NonNull UUID id)  {
        try {
            requestExistMessage(id);
        } catch (NoDataForOperationException e) {
            return 0;
        }
        return this.table.get(id);
    }

}
