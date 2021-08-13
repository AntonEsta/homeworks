package chat;

import chat.data.UserID;
import chat.exception.UserException;
import lombok.NonNull;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserTable {

    final Map<UserID, @NonNull String> users = new ConcurrentHashMap<>();

    public UserID addUser(@NonNull String nickname) throws UserException {
        if ( isNicknameUsed(nickname) ) throw new UserException("Not allowed nickname!");
        UserID userID = new UserID();
        try {
            if (this.users.put(userID, nickname) != null) {
                return userID;
            }
        } catch (Exception e) {
            throw new UserException("Can't add user.", e);
        }
        return null;
    }

    public String deleteUser(@NonNull UserID id) {
        return this.users.remove(id);
    }

    public boolean isUserExists(@NonNull UserID id) {
        if (this.users.isEmpty()) {
            return false;
        }
        return this.users.containsKey(id);
    }

    public boolean isNicknameUsed(@NonNull String nickname) {
        if (this.users.isEmpty()) {
            return false;
        }
        return this.users.containsValue(nickname);
    }


    public UserID getUserID(@NonNull String nickname) {

        for (Map.Entry<UserID, String> userIDStringEntry : this.users.entrySet()) {
            if (userIDStringEntry.getValue().equals(nickname)) {
                return userIDStringEntry.getKey();
            }
        }

        return null;
    }
}
