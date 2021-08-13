package chat.data;

import chat.MessageTable;
import chat.UserMessagesTable;
import chat.UserTable;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import chat.exception.UserException;

import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataBase {

    final UserTable users = new UserTable();
    final MessageTable messages = new MessageTable();
    final UserMessagesTable userMessages = new UserMessagesTable();

    public UserID addUser(@NonNull String nickname) throws UserException {
        if ( this.users.isNicknameUsed(nickname) ) throw new UserException("Not allowed nickname!");
        UserID userID = new UserID();
        try {
            if (this.users.addUser(nickname) != null) {
                return null;
            }
            userMessages.addUser(userID); //addUserMessages(userID);
        } catch (Exception e) {
            this.users.deleteUser(userID);
            throw new UserException("Can't add user.");
        }
        return userID; //TODO maybe trouble !!!
    }

    public void messageWasRead(@NonNull UserID userID, @NonNull UUID messageID) {
        synchronized (this) {
            this.userMessages.deleteMessage(userID, messageID);
        }
        // TODO mark message as read
    }

    private void checkForDefinedUser(@NonNull UserID userID) throws UserException {
        if (!isDefinedUser(userID)) {
            throw new UserException("User not defined.");
        }
    }

    private boolean isDefinedUser(@NonNull UserID userID) {
        return this.users.isUserExists(userID);
    }

    public void addMessage(@NonNull Message message, UserID... userIDs) throws UserException {
        synchronized (this) {
            System.out.println("DataBase addMessage()");
            if (isDefinedUser(message.getFromUser())) throw new UserException("No such user.");
            try {
                UUID messageID = this.messages.addMessage(message);
                System.out.println("Set Message ID " + messageID);
                if (messageID != null) {
                    userMessages.addMessage(messageID, userIDs);
                }
            } catch (UserException e) {

            }
        }
    }

    public boolean deleteUser(@NonNull UserID userID) throws UserException {
            // TODO уточнить ход действий
        if (this.users.deleteUser(userID) == null) return false;
        this.userMessages.deleteUser(userID);
        return true;
    }

    public UserID getUserID(@NonNull String nickname) {
        return this.users.getUserID(nickname);
    }

}