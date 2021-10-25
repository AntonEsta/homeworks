package chat.database.tables;

import chat.user.User;
import chat.user.UserID;
import chat.user.UserException;
import lombok.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class stores information about registered users.
 */
public class UserTable {

    final Map<UserID, @NonNull String> users = new ConcurrentHashMap<>();

    /**
     * Adds the user.
     * @param nickname The user's nickname.
     * @return {@link UserID} if the user added successful.
     * @throws UserException throws if {@param nickname} is not allowed or can't add the user because of throws other exceptions.
     */
    public UserID addUser(@NonNull String nickname) throws UserException {
        if ( isNicknameUsed(nickname) ) throw new UserException("Not allowed nickname!");
        UserID userID = new UserID();
        try {
            this.users.put(userID, nickname);
            return userID;
        } catch (Exception e) {
            throw new UserException("Can't add user.", e);
        }
    }

    /**
     * Deletes user.
     * @param id {@link UserID}.
     * @return The deleted user's nickname or {@code null} if there was no user ID.
     */
    public String deleteUser(@NonNull UserID id) {
        return this.users.remove(id);
    }

    /**
     * Checks the user exist or not.
     * @param id {@link UserID}.
     * @return {@code true} if the user is exist and {@code false} else.
     */
    public boolean isUserExists(@NonNull UserID id) {
        if (this.users.isEmpty()) {
            return false;
        }
        return this.users.containsKey(id);
    }

    /**
     * For checking used nickname or not.
     * @param nickname the nickname.
     * @return {@code true} if nickname is used or {@code false} else.
     */
    public boolean isNicknameUsed(@NonNull String nickname) {
        if (this.users.isEmpty()) {
            return false;
        }
        return this.users.containsValue(nickname);
    }

    /**
     * Returns {@link User} by his {@link UserID}.
     * @param id {@link UserID}
     * @return {@link User}
     * @throws UserException throws if there is no user with this id.
     */
    public User getUser(@NonNull UserID id) throws UserException{
        for (Map.Entry<UserID, String> userIDStringEntry : this.users.entrySet()) {
            if (userIDStringEntry.getKey().equals(id)) {
                return new User(id, userIDStringEntry.getValue());
            }
        }
        throw new UserException("There is no user with this id.");
    }

    /**
     * Returns {@link User} by his nickname.
     * @param nickname {@link String} nickname.
     * @return {@link User}
     * @throws UserException throws if there is no user with this nickname.
     */
    public User getUser(@NonNull String nickname) throws UserException {
        for (Map.Entry<UserID, String> userIDStringEntry : this.users.entrySet()) {
            if (userIDStringEntry.getValue().equals(nickname)) {
                return new User(userIDStringEntry.getKey(), nickname);
            }
        }
        throw new UserException("There is no user with this nickname.");
    }

}
