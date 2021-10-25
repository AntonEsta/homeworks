package chat.message;

import chat.user.UserID;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    /**
     * Returns new message of acknowledgment receive the message.
     * @param messageID the message whose receive.
     * @return the new message
     */
    public static MessagePacket newAcknowledgmentMessage(UUID messageID) {
        return builder()
                .messageID(messageID)
                .messageType(MessageType.ACKNOWLEDGMENT)
                .build();
    }

    /**
     * Returns new message of registration the new user.
     * @param nickname the new user nickname.
     * @return the new message.
     */
    public static MessagePacket newRegReqMessage(String nickname) {
        return builder()
                .messageID(UUID.randomUUID())
                .messageType(MessageType.REGISTRATION_REQUEST)
                .content(nickname)
                .build();
    }

    /**
     * Returns new refuse message of the new user registration.
     * @return the new message
     */
    public static MessagePacket newRefuseReqMessage() {
        return builder()
                .messageID(UUID.randomUUID())
                .messageType(MessageType.REGISTRATION_REFUSED)
                .build();
    }

    /**
     * Returns the new message from user to the another user.
     * @param fromUser the user ID whose sends message.
     * @param toUser the user ID to gets message.
     * @param content the message content.
     * @return the message for sends.
     */
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

    /**
     * Returns the new message for request information about user.
     * @param fromUser the user ID for whose needs find nickname.
     * @param nickname the user nickname for whose needs find ID.
     * @return the new message.
     */
    public static MessagePacket newWhoisMessage(UserID fromUser, String nickname) {
        return builder()
                .messageID(UUID.randomUUID())
                .fromUser(fromUser)
                .messageType(MessageType.WHOIS)
                .content(nickname)
                .build();
    }

    /**
     * Returns the new message for detach user from server.
     * @param fromUser the user ID whose will be detach.
     * @return the new message.
     */
    public static MessagePacket newDetachUserMessage(UserID fromUser) {
        return builder()
                .messageID(UUID.randomUUID())
                .fromUser(fromUser)
                .messageType(MessageType.DETACH_USER)
                .build();
    }

    /**
     * Returns the new message about successful registration the new user.
     * @param toUser the user ID whose was successful registered.
     * @param nickname the user nickname whose was successful registered.
     * @return the new message.
     */
    public static MessagePacket newConfirmReqMessage(UserID toUser, String nickname) {
        return builder()
                .messageID(UUID.randomUUID())
                .toUser(new UserID[]{toUser})
                .messageType(MessageType.REGISTRATION_CONFIRMED)
                .content(nickname)
                .build();
    }

    /**
     * Returns the new message of user new messages request.
     * @param fromUser the user ID whose request new messages.
     * @return the new message.
     */
    public static MessagePacket newReqNewMessage(UserID fromUser) {
        return builder()
                .messageID(UUID.randomUUID())
                .fromUser(fromUser)
                .messageType(MessageType.REQUEST_NEW)
                .build();
    }

    /**
     * Returns the new message of messages as answer from new messages request.
     * @param toUserID the user ID whose required anew messages.
     * @param messages the messages themselves in the format {@link Message}
     * @return the new message.
     */
    public static MessagePacket newMessages(UserID toUserID, Message[] messages) {
        return builder()
                .messageID(UUID.randomUUID())
                .toUser(new UserID[]{toUserID})
                .messageType(MessageType.MESSAGE)
                .content(messages)
                .build();
    }

    /**
     * Returns true if the message is confirmation of new user registration.
     * @return true if the message is confirmation of new user registration and false else.
     */
    public boolean isConfirmationOfRegistration() {
        return this.messageType.equals(MessageType.REGISTRATION_CONFIRMED);
    }

    /**
     * Returns true if the message is acknowledge message of delivery.
     * @return true if the message is acknowledge message of delivery and false else.
     */
    public boolean isAckMessage() {
        return this.messageType.equals(MessageType.ACKNOWLEDGMENT);
    }

    /**
     * Returns true if the message is acknowledge of the message delivery.
     * @param messageID the message ID whose was delivery.
     * @return true if the message is acknowledge of the message delivery and false else.
     */
    public boolean isAckMessage(UUID messageID) {
        return this.messageType.equals(MessageType.ACKNOWLEDGMENT) && this.messageID.equals(messageID);
    }

    /**
     * Returns true if the message is the user registration request.
     * @return true if the message is the user registration request and false else.
     */
    public boolean isRegReqMessage() {
        return this.messageType.equals(MessageType.REGISTRATION_REQUEST) && (this.content instanceof String);
    }

    /**
     * Returns is it the message is the user detach request or not.
     * @return true if the message is the user detach request and false else.
     */
    public boolean isDetachUsrMessage() {
        return this.messageType.equals(MessageType.DETACH_USER) && (this.fromUser != null);
    }

    /**
     * Returns is it the message is new messages request or not.
     * @return true if the message is new messages request and false else.
     */
    public boolean isNewMessages() {
        return this.messageType.equals(MessageType.MESSAGE) && isMessagesContent();
    }

    /**
     * Returns is it the message is new message for some user(s) or not.
     * @return true if the message is new message and false else.
     */
    public boolean isNewMessage() {
        return this.messageType.equals(MessageType.MESSAGE) && isMessageContent();
    }

    /**
     * Returns is it the message with single message content.
     * @return true if the message consider is single message content.
     */
    private boolean isMessageContent() {
        return this.content instanceof Message;
    }

    /**
     * Returns it's the message with array of messages content or not.
     * @return true if the message consider are many messages.
     */
    private boolean isMessagesContent() {
        return this.content instanceof Message[];
    }

    /**
     * Returns it is the "who is" message or not.
     * @return true if the "who is" message.
     */
    public boolean isWhoIsMessage() {
        return this.messageType.equals(MessageType.WHOIS); // && (this.content instanceof String);
    }
}
