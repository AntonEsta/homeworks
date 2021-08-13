package chat;

import chat.data.Message;
import chat.data.MessagePacket;
import chat.data.DataBase;
import chat.data.UserID;
import chat.exception.MessageFormatException;
import chat.exception.UserException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientSupport implements Runnable {

    final DataBase db;
    final ConfirmingMessenger messenger;

    public ClientSupport(@NonNull Socket socket, @NonNull DataBase db) throws IOException {
        this.messenger = new ConfirmingMessenger(socket);
        this.db = db;
    }

    private void receiveManager(@NonNull MessagePacket msg) throws IOException, MessageFormatException {

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
            case WHOIS:
                whoIs(msg);
                break;
            // processing new user message
            case MESSAGE:
                newUserMessage(msg);
                break;
            default:
                System.out.println(">>>");
                throw new MessageFormatException("The message format is incorrect.");
        }
    }

    private void whoIs(@NonNull MessagePacket msg) {
        if ( !msg.isWhoIsMessage() ) throw new MessageFormatException("Is not who is message.");
        String nickname = (String) msg.getContent();
        UserID id = this.db.getUserID(nickname);
        MessagePacket message = MessagePacket.newWhoisMessage(id, nickname);
        this.messenger.sendMessage(message);
    }

    private void newUserMessage(@NonNull MessagePacket msg) {
        System.out.println("!!!");
        if (!msg.isNewMessage()) throw new MessageFormatException("The format does not match the new message.");
        System.out.println("@@@");
        Message message = new Message(msg.getFromUser(), msg.getContent());
        this.db.addMessage(message, msg.getToUser());
    }

    private void detachUser(@NonNull MessagePacket msg) {
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

    private void regUser(@NonNull MessagePacket msg) throws IOException {
        if (!msg.isRegReqMessage()) throw new MessageFormatException("The format does not match the user registration request.");
        UserID[] usrID = new UserID[1];
        String nickname = (String) msg.getContent();
        try {
            usrID[0] = this.db.addUser(nickname);
        } catch (UserException e) {
            msg = MessagePacket.newRefuseReqMessage();
            this.messenger.sendMessage(msg);
            return;
        }
        MessagePacket confirmReqMessage = MessagePacket.newConfirmReqMessage(usrID, nickname);
        this.messenger.sendMessage(confirmReqMessage);
    }

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Start listen..." + this);
//        int countCycle = 0;
        while ( !this.messenger.isClosed() ) {
//            System.out.println("Cycle: " + (++countCycle));
            MessagePacket msg = null;
            try {
                msg = this.messenger.getMessage();
                receiveManager(msg);
            } catch (MessageFormatException ignored) {
//                e.getStackTrace();
            }
        }
        throw new SocketException("Socket is closed!");
    }
}
