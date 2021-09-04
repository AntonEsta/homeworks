package chat.database;

import chat.database.tables.MessageTable;
import chat.database.tables.NoDataForOperationException;
import chat.database.tables.usermessages.UserMessagesTable;
import chat.database.tables.UserTable;
import chat.message.Message;
import chat.user.UserID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import chat.user.UserException;

import java.util.*;


/**
 * The class stores information and organizes work with system data.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataBase {

    // Stores information about users.
    final UserTable users = new UserTable();

    // Store messages.
    final MessageTable messages = new MessageTable();

    // Store connections between users and messages.
    final UserMessagesTable userMessages = new UserMessagesTable();

    /**
     * Adds new user to base.
     * @param nickname nickname of user
     * @return {@link UserID} if user create successful.
     * @throws UserException throws when not creates user or any exception catch.
     */
    public UserID addUser(@NonNull String nickname) throws UserException {
        if ( this.users.isNicknameUsed(nickname) ) throw new UserException("Not allowed nickname!");
        UserID userID = null;
        try {
            userID = this.users.addUser(nickname);
            if(userID != null) {
                userMessages.addUser(userID);
            }
        } catch (Exception e) {
            if (userID != null) {
                this.users.deleteUser(userID);
            }
            throw new UserException("Can't add user.");
        }
        return userID;
    }

    /**
     * Deletes rode messages from user's messages.
     * @param userID The user ID.
     * @param messageID Messages IDs for deleting.
     */
    public void messageWasRead(@NonNull UserID userID, UUID... messageID) {
        if (!isDefinedUser(userID)) return;
        synchronized (this) {
            if (messageID == null) return;
            for (UUID uuid : messageID) {
                try {
                    this.userMessages.deleteMessage(userID, uuid);
                    if (!this.userMessages.isActualMessage(uuid)) {
                        this.messages.deleteMessage(uuid);
                    }
                } catch (NullPointerException | NoDataForOperationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Checks user defined in base. Work like {@code Objects.requireNonNull()}
     * @param userID The user ID.
     * @throws UserException throws when user not defined
     */
    private void checkForDefinedUser(@NonNull UserID userID) throws UserException {
        if (!isDefinedUser(userID)) {
            throw new UserException("User not defined.");
        }
    }

    /**
     * Returns {@code true} if user is exists in base and {@code false} else.
     * @param userID the user ID.
     * @return {@code true} if user is exists in base and {@code false} else.
     */
    private boolean isDefinedUser(@NonNull UserID userID) {
        return this.users.isUserExists(userID);
    }

    public void addMessage(@NonNull Message message, UserID... userIDs) throws UserException {
        synchronized (this) {
            if (!isDefinedUser(message.getFromUser())) throw new UserException("No such user.");
            try {
                UUID messageID = this.messages.addMessage(message);
                if (messageID != null) {
                    userMessages.addMessage(messageID, userIDs);
                }
            } catch (Exception e) {
                throw new UserException("Can't add message.", e);
            }
        }
    }


    /**
     * Deletes user from base.
     * @param userID the user id.
     * @return {@code true} if the user deleted successful.
     * @throws UserException thrown if the user not exist.
     */
    public boolean deleteUser(@NonNull UserID userID) throws UserException {
        if (this.users.deleteUser(userID) == null) return false;
        this.userMessages.deleteUser(userID);
        return true;
    }

    /**
     * Returns the user ID by his nickname.
     * @param nickname the user nick.
     * @return the user ID.
     */
    public UserID getUserID(@NonNull String nickname) {
        return this.users.getUser(nickname).getId();
    }

    /**
     * Returns messages by user ID.
     * @param userID the user ID.
     * @return {@link MessageTable} for the user.
     */
    public MessageTable getMessages(@NonNull UserID userID) {
        checkForDefinedUser(userID);
        Map<UUID, Message> messageMap = new HashMap<>();
        UUID[] messagesUUIDs = this.userMessages.getMessages(userID);
        if (messagesUUIDs.length > 0) {
            for (UUID messagesUUID : messagesUUIDs) {
                final Message message = this.messages.getMessage(messagesUUID);
                if (message != null) {
                    messageMap.put(messagesUUID, message);
                }
            }
        }
        return new MessageTable(messageMap);
    }

    /**
     * Returns the user nickname.
     * @param id The user ID.
     * @return The user nickname.
     */
    public String getUserNickname(UserID id) {
        return this.users.getUser(id).getNickname();
    }

}