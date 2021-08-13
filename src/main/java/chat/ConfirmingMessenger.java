package chat;

import chat.data.MessagePacket;
import chat.exception.MessageFormatException;
import lombok.NonNull;

import java.io.IOException;
import java.net.Socket;

public class ConfirmingMessenger extends SimpleMessenger {


    public ConfirmingMessenger(@NonNull Socket socket) throws IOException {
        super(socket);
    }

    private void sendAckMessage(@NonNull MessagePacket message) throws IOException, MessageConfirmationException {
        if ( message.isAckMessage() ) return; //throw new MessageConfirmationException("The message does not need confirmation.");
        MessagePacket msg = MessagePacket.newAcknowledgmentMessage(message.getMessageID());
        sendMessage(msg);
    }

    private void getAckMessage(@NonNull MessagePacket msg) throws IOException, ClassNotFoundException, MessageConfirmationException, MessageFormatException {
        // TODO разобрать экзепшены
        if (msg.isAckMessage()) return;

        MessagePacket receiveMessage = getMessage();

        if (receiveMessage != null) {
            if ( receiveMessage.isAckMessage(msg.getMessageID()) ) return;
        }
        throw new MessageConfirmationException("Received a message other than acknowledgment.");
//        throw new MessageFormatException();
    }

    @Override
    public void sendMessage(@NonNull MessagePacket msg) {
        // send the message
        super.sendMessage(msg);
        // wait conformation of delivery
        if (!msg.isAckMessage()) {
            try {
                getAckMessage(msg);
            } catch (IOException | ClassNotFoundException e) {
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
        try {
            sendAckMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }


}
