package chat.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable {

    private static final long serialVersionUID = -7203064836075087433L;
    String nickname;
    UUID sessionID;
//    InetAddress ipAddress;
//    int port;

}
