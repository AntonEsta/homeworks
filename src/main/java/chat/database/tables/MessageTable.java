package chat.database.tables;

import chat.message.Message;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class stores messages received from users of the system.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageTable {

    final Map<UUID, @NonNull Message> messages;

    public MessageTable() {
        this.messages = new ConcurrentHashMap<>();
    }

    public MessageTable(Map<UUID, @NonNull Message> messages) {
        this.messages = new ConcurrentHashMap<>(messages);
    }

    /**
     * Adds message in storage.
     * @param message message to add
     * @return ID of message
     */
    public synchronized UUID addMessage(@NonNull Message message) {
        UUID messageID = UUID.randomUUID();
        this.messages.put(messageID, message);
        return messageID;
    }

    public void deleteMessage(@NonNull UUID id) {
        this.messages.remove(id);
    }

    /**
     * Returns the message by message ID.
     * @param id message id.
     * @return the message.
     */
    public Message getMessage(@NonNull UUID id) {
        return this.messages.get(id);
    }

    /**
     * Returns messages array of storage.
     * @return array of messages.
     */
    public Message[] getMessages() {
        final Collection<@NonNull Message> values = this.messages.values();
        return values.toArray(new Message[0]);
    }

    /**
     * Returns message IDs array of storage.
     * @return message IDs
     */
    public UUID[] getIDs() {
        final Set<UUID> uuids = this.messages.keySet();
        return uuids.toArray(new UUID[0]);
    }
}
