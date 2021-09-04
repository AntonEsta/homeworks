package chat.server;

import chat.message.Message;
import chat.message.MessagePacket;
import chat.database.DataBase;
import chat.user.UserID;
import chat.database.tables.MessageTable;
import chat.message.MessageFormatException;
import chat.message.MessageTimeoutException;
import chat.user.UserException;
import chat.messanger.ConfirmingMessenger;
import chat.messanger.MessageConfirmationException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.UUID;

/**
 * Class for working with a dedicated client connection.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
class ClientSupport implements Runnable {

    // Base holding information for system work.
    final DataBase db;
    // Messenger for communication.
    final ConfirmingMessenger messenger;
    // Timeout of socket answer waiting.
    static final int defaultSocketTimeout = 10_000;

    public ClientSupport(@NonNull Socket socket, @NonNull DataBase db) throws IOException {
        this(socket, defaultSocketTimeout, db);
    }

    public ClientSupport(@NonNull Socket socket, int socketTimeout, @NonNull DataBase db) throws IOException {
        this.messenger = new ConfirmingMessenger(socket, socketTimeout);
        this.db = db;
    }

    /**
     * Distribution of messages by required operations.
     * @param msg the message
     * @throws MessageFormatException throw if message format is incorrect.
     */
    private void receiveManager(@NonNull MessagePacket msg) throws MessageFormatException {
        // add new user
        switch (msg.getMessageType()) {
            // registration user
            case REGISTRATION_REQUEST:
                regUser(msg);
                break;
            // delete user
            case DETACH_USER:
                detachUser(msg);
                break;
            // user identification
            case WHOIS:
                whoIs(msg);
                break;
            // processing new user message
            case MESSAGE:
                newUserMessage(msg);
                break;
            // get new messages
            case REQUEST_NEW:
                giveNewMessages(msg.getFromUser());
                break;
            default:
                throw new MessageFormatException("The message format is incorrect.");
        }
    }

    /**
     * Get new messages.
     * @param userID user ID who is wants get messages.
     */
    private void giveNewMessages(UserID userID) {
        MessageTable messageMap = this.db.getMessages(userID);
        final Message[] messages = messageMap.getMessages();
        MessagePacket msg = MessagePacket.newMessages(userID, messages);
        try {
            this.messenger.sendMessage(msg);
        } catch (MessageConfirmationException ignored) { }
        final UUID[] messageID = messageMap.getIDs();
        this.db.messageWasRead(userID, messageID);
    }

    /**
     * Processing the message containing a request to identify a user.
     * @param msg the message for processing.
     * @exception MessageFormatException if the message are not is "who is" message.
     */
    private void whoIs(@NonNull MessagePacket msg) throws MessageFormatException {
        if ( !msg.isWhoIsMessage() ) throw new MessageFormatException("Is not who is message.");
        UserID id = msg.getFromUser();
        String nickname = (String) msg.getContent();
        if (nickname != null) {
            id = this.db.getUserID(nickname);
        }
        if (nickname == null) {
            nickname = this.db.getUserNickname(id);
        }
        MessagePacket message = MessagePacket.newWhoisMessage(id, nickname);
        this.messenger.sendMessage(message);
    }

    /**
     * Registrations the new message for user.
     * @param msg the message
     * @throws MessageFormatException if format of the message is not new message.
     */
    private void newUserMessage(@NonNull MessagePacket msg) throws MessageFormatException {
        if (!msg.isNewMessage()) throw new MessageFormatException("The format does not match the new message.");
        Message message = new Message(msg.getFromUser(), msg.getContent());
        this.db.addMessage(message, msg.getToUser());
    }

    /**
     * Handling a request to detach the user.
     * @param msg the message
     * @exception MessageFormatException if format of the message is not detach request.
     */
    private void detachUser(@NonNull MessagePacket msg) throws MessageFormatException {
        if (!msg.isDetachUsrMessage()) throw new MessageFormatException("The format does not match the user detach request.");
        try {
            if (this.db.deleteUser(msg.getFromUser())) {
                MessagePacket ackMessage = MessagePacket.newAcknowledgmentMessage(msg.getMessageID());
                this.messenger.sendMessage(ackMessage);
            }
        } catch (UserException | MessageConfirmationException e) {
            e.getStackTrace();
        }
    }

    /**
     * User registration.
     * @param msg the message for registration.
     * @throws MessageFormatException the format does not match the user registration request.
     */
    private void regUser(@NonNull MessagePacket msg) throws MessageFormatException {
        if (!msg.isRegReqMessage()) throw new MessageFormatException("The format does not match the user registration request.");
        UserID usrID;
        String nickname = (String) msg.getContent();
        try {
            usrID = this.db.addUser(nickname);
        } catch (UserException e) {
            msg = MessagePacket.newRefuseReqMessage();
            this.messenger.sendMessage(msg);
            return;
        }
        MessagePacket confirmReqMessage = MessagePacket.newConfirmReqMessage(usrID, nickname);
        this.messenger.sendMessage(confirmReqMessage);
    }

    /**
     * Runs service.
     */
    @SneakyThrows
    @Override
    public void run() {
        while ( !this.messenger.isClosed() ) {
            MessagePacket msg;
            try {
                msg = this.messenger.getMessage();
                receiveManager(msg);
            } catch (MessageFormatException | MessageTimeoutException ignored) {
            }
        }
        throw new SocketException("Socket is closed!");
    }
}
