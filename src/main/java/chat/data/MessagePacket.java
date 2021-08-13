package chat.data;

import lombok.*;
import lombok.experimental.FieldDefaults;
import chat.MessageType;

import java.io.Serializable;
import java.util.UUID;


@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MessagePacket implements Serializable {

    private static final long serialVersionUID = 1606878786008241556L;

    final UUID messageID;
    final UserID fromUser;
    final UserID[] toUser;
    final MessageType messageType;
    final Object content;

    public static MessagePacket newAcknowledgmentMessage(UUID messageID) {
        return builder()
                .messageID(messageID)
//                .fromUser(fromUser)
                .messageType(MessageType.ACKNOWLEDGMENT)
                .build();
    }

    public static MessagePacket newRegReqMessage(String nickname) {
        return builder()
                .messageID(UUID.randomUUID())
                .messageType(MessageType.REGISTRATION_REQUEST)
                .content(nickname)
                .build();
    }

    public static MessagePacket newRefuseReqMessage() {
        return builder()
                .messageID(UUID.randomUUID())
                .messageType(MessageType.REGISTRATION_REFUSED)
                .build();
    }

    public static MessagePacket newMessage(@NonNull UserID fromUser, @NonNull UserID[] toUser, @NonNull Object content) {
        Message message = new Message(fromUser, content);
        return builder()
                .messageID(UUID.randomUUID())
                .fromUser(fromUser)
                .toUser(toUser)
                .messageType(MessageType.MESSAGE)
                .content(message)
                .build();
    }

    public static MessagePacket newWhoisMessage(UserID fromUser, String nickname) {
        return builder()
                .messageID(UUID.randomUUID())
                .fromUser(fromUser)
//                .toUser(toUser)
                .messageType(MessageType.WHOIS)
                .content(nickname)
                .build();
    }

    public static MessagePacket newDetachUserMessage(UserID fromUser) {
        return builder()
                .messageID(UUID.randomUUID())
                .fromUser(fromUser)
                .messageType(MessageType.DETACH_USER)
                .build();
    }

    public static MessagePacket newConfirmReqMessage(UserID[] toUser, String nickname) {
        return builder()
                .messageID(UUID.randomUUID())
                .toUser(toUser)
                .messageType(MessageType.REGISTRATION_CONFIRMED)
                .content(nickname)
                .build();
    }

    public static MessagePacket newReqNewMessage(UserID fromUser) {
        return builder()
                .messageID(UUID.randomUUID())
                .fromUser(fromUser)
                .messageType(MessageType.REQUEST_NEW)
                .build();
    }


    public boolean isConfirmationOfRegistration() {
        return this.messageType.equals(MessageType.REGISTRATION_CONFIRMED);
    }

    public boolean isAckMessage() {
        return this.messageType.equals(MessageType.ACKNOWLEDGMENT);
    }

    public boolean isAckMessage(UUID messageID) {
        return this.messageType.equals(MessageType.ACKNOWLEDGMENT) && this.messageID.equals(messageID);
    }

    public boolean isRegReqMessage() {
        return this.messageType.equals(MessageType.REGISTRATION_REQUEST) && (this.content instanceof String);
    }

    public boolean isDetachUsrMessage() {
        return this.messageType.equals(MessageType.DETACH_USER) && (this.fromUser != null);
    }

    public boolean isNewSingleMessageFromUser() {
        return this.messageType.equals(MessageType.MESSAGE) && (this.fromUser != null);
    }

    public boolean isNewMessages() {
        return this.messageType.equals(MessageType.MESSAGE) && isMessagesContent();
    }

    public boolean isNewMessage() {
        return this.messageType.equals(MessageType.MESSAGE) && isMessageContent();
    }

    private boolean isMessageContent() {
        return this.content instanceof Message;
    }

    private boolean isMessagesContent() {
        return this.content instanceof Message[];
    }

    public boolean isWhoIsMessage() {
        return this.messageType.equals(MessageType.WHOIS) && (this.content instanceof String);
    }
}
