package chat.messanger;

import chat.message.MessagePacket;
import lombok.NonNull;

import java.io.IOException;
import java.net.Socket;

/**
 * Messenger implementation with confirming delivery.
 */
public class ConfirmingMessenger extends SimpleMessenger {

    public ConfirmingMessenger(@NonNull Socket socket) throws IOException {
        super(socket);
    }

    public ConfirmingMessenger(@NonNull Socket socket, int socketTimeout) throws IOException {
        super(socket, socketTimeout);
    }

    /**
     * Sends acknowledgment message.
     * @param message message to be confirmed.
     */
    private void sendAckMessage(@NonNull MessagePacket message) {
        if ( message.isAckMessage() ) return;
        MessagePacket msg = MessagePacket.newAcknowledgmentMessage(message.getMessageID());
        sendMessage(msg);
    }

    /**
     * Sends acknowledgment message.
     * @param msg message to be confirmed.
     */
    private void getAckMessage(@NonNull MessagePacket msg) throws MessageConfirmationException {
        if (msg.isAckMessage()) return;

        MessagePacket receiveMessage = getMessage();

        if (receiveMessage != null) {
            if ( receiveMessage.isAckMessage(msg.getMessageID()) ) return;
        }
        throw new MessageConfirmationException("Received a message other than acknowledgment.");
    }

    @Override
    public void sendMessage(@NonNull MessagePacket msg) throws MessageConfirmationException {
        // send the message
        super.sendMessage(msg);
        // wait conformation of delivery
        if (!msg.isAckMessage()) {
            try {
                getAckMessage(msg);
            } catch (Exception e) {
                throw new MessageConfirmationException("No confirmation of message delivery.", e);
            }
        }
   }

    @Override
    public MessagePacket getMessage() {
        // get message
        MessagePacket msg = super.getMessage();
        if (msg == null) return null;
        // send confirmation of delivery
        sendAckMessage(msg);
        return msg;
    }


}
