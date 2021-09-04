package chat.message;

import chat.user.UserID;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Objects;

/**
 * The class contains information about the message.
 */
@Getter
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Message implements Serializable {

    private static final long serialVersionUID = -3859398354295928527L;

    // user ID whose message is.
    final UserID fromUser;
    // message content
    final Object content;

    public Message(UserID fromUser, Object content) {
        this.fromUser = fromUser;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(fromUser, message.fromUser) && Objects.equals(content, message.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromUser, content);
    }
}
