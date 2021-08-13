package chat;

import chat.data.UserID;
import chat.exception.UserException;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserMessagesTable {

    final Map<UserID, @NonNull Set<UUID>> userMessages = new ConcurrentHashMap<>();

    public void addUser(UserID id) {
        Set<UUID> uuidSet = this.userMessages.put(id, new HashSet<>());
        if (uuidSet != null) {
            throw new UserException("Can't create the set of messages for user.");
        }
    }

    public void deleteUser(UserID id) {
        this.userMessages.remove(id);
    }

    public void addMessage(@NonNull UUID messageID, UserID... userIDs) throws UserException {
        if (userIDs == null) {
            this.userMessages.forEach((u, m) -> m.add(messageID));
        } else {
            for (UserID userID : userIDs) {
                if (userID != null) {
                    // TODO null
                    Set<UUID> messages = this.userMessages.get(userID);
                    System.out.println("get.userID " + userID);
                    if (messages != null) {
                        System.out.println("Add message for user " + messages.add(messageID));
                    }
                }
            }
        }
    }

    public void deleteMessage(@NonNull UserID userID, @NonNull UUID messageID) {
        this.userMessages.get(userID).remove(messageID);
    }

    public void deleteMessages(@NonNull UUID messageID) {
        this.userMessages.forEach((userID, uuids) -> uuids.remove(messageID));
    }

}
