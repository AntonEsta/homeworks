package chat.database.tables.usermessages;

import chat.database.tables.NoDataForOperationException;
import chat.user.UserID;
import chat.exception.OperationException;
import chat.user.UserException;
import lombok.NonNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class considers relations between users and messages addressed to him.
 */
public class UserMessagesTable {

    final Map<@NonNull UserID, @NonNull Set<UUID>> userMessages = new ConcurrentHashMap<>();
    final MessageCounter messageCounter = new MessageCounter();

    /**
     * Adds message relations for new user.
     * @param id new user ID.
     */
    public void addUser(@NonNull UserID id) {
        try {
            this.userMessages.put(id, new HashSet<>());
        } catch (Exception e) {
            throw new UserException("Can't create the set of messages for user.", e);
        }
    }

    /**
     * Deletes user.
     * @param id The user ID.
     */
    public void deleteUser(@NonNull UserID id) {
        final Set<UUID> uuids = this.userMessages.get(id);
        for (UUID msgID : uuids) {
            this.messageCounter.deleteMessage(msgID);
        }
        this.userMessages.remove(id);
    }

    /**
     * Adds message ID to user-message relations.
     * @param messageID The message {@link UUID}.
     * @param userIDs The {@link UserID}(s) user who needs to add the message.
     * @throws UserException throws then can't add message for the user.
     */
    public void addMessage(@NonNull UUID messageID, UserID... userIDs) throws UserException {
        if (userIDs == null || userIDs.length == 0) {
            this.userMessages.forEach((u, m) -> {
                m.add(messageID);
                this.messageCounter.addMessage(messageID);
            });
        } else {
            for (UserID userID : userIDs) {
                if (userID != null) {
                    Set<UUID> messages = this.userMessages.get(userID);
                    try {
                        messages.add(messageID);
                        messageCounter.addMessage(messageID);
                    } catch (Exception e) {
                        throw new UserException("Can't add message for user.", e);
                    }
                }
            }
        }
    }

    /**
     * Deletes the message from user-message relations.
     * @param userID The user ID.
     * @param messageID The message {@link UUID}.
     */
    public void deleteMessage(@NonNull UserID userID, @NonNull UUID messageID) throws NullPointerException, NoDataForOperationException {
        final Set<UUID> uuidSet = this.userMessages.get(userID);
        Objects.requireNonNull(uuidSet);
        if (uuidSet.remove(messageID)) {
            messageCounter.deleteMessage(messageID);
        }
    }

    /**
     * Returns true if the message is actual in system.
     * @param id message ID.
     * @return true if the message is actual in system.
     */
    public boolean isActualMessage(@NonNull UUID id) {
        try {
            int count = this.messageCounter.getCount(id);
            if (count > 0) return true;
            return false;
        } catch (OperationException e) {
            return false;
        }
    }

    public void deleteMessages(@NonNull UUID messageID) {
        this.userMessages.forEach((userID, uuids) -> uuids.remove(messageID));
    }

    /**
     * Returns IDs of user's messages.
     * @param userID user ID whose messages will returns.
     * @return ID's messages.
     */
    public UUID[] getMessages(UserID userID) {
        try {
            final Set<UUID> uuids = this.userMessages.get(userID);
            return uuids.toArray(new UUID[0]);
        } catch (Exception e) {
            throw new OperationException("Can't get messages for user " + userID, e);
        }
    }

}
