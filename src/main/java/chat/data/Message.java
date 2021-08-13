package chat.data;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Message implements Serializable {

    private static final long serialVersionUID = -3859398354295928527L;

    final UserID fromUser;
    final Object content;

    public Message(UserID fromUser, Object content) {
        this.fromUser = fromUser;
        this.content = content;
    }

}
