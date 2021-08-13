package chat;

import chat.data.Message;
import chat.exception.UserException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageTable {

    final Map<UUID, @NonNull Message> messages;

    public MessageTable() {
        this.messages = new ConcurrentHashMap<>();
    }

    public MessageTable(@NonNull MessageTable table) {
        this.messages = table.messages;
    }

    public synchronized UUID addMessage(@NonNull Message message) throws UserException {
        UUID messageID = UUID.randomUUID();
        System.out.println("UUID : " + messageID);
        this.messages.put(messageID, message);
        return messageID;
    }

    public Message deleteMessage(UUID id) {
        return this.messages.remove(id);
    }

}
