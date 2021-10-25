package chat.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

/**
 * Class consider the user ID.
 */
@ToString
@EqualsAndHashCode
public class UserID implements Serializable {
    private static final long serialVersionUID = 9175377831734316696L;
    @Getter
    private final UUID ID;

    public UserID() {
        this.ID = UUID.randomUUID();
    }

    public UserID(UUID id) {
        this.ID = id;
    }

}
