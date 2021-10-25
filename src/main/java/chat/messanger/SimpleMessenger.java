package chat.messanger;

import chat.message.MessagePacket;
import chat.message.MessageFormatException;
import chat.message.MessageTimeoutException;
import lombok.Getter;
import lombok.NonNull;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.UUID;

/**
 * Simple messenger implementation.
 */
public class SimpleMessenger implements Messenger<MessagePacket>, AutoCloseable {

    private final Socket socket;
    @Getter
    private final UUID sessionID;

    public SimpleMessenger(@NonNull Socket socket) throws IOException {
        if (socket.isClosed()) throw new SocketException("Socket is closed. (session ID: " + getSessionID());
        this.socket = socket;
        this.sessionID = UUID.randomUUID();
    }

    public SimpleMessenger(@NonNull Socket socket, int timeout) throws IOException {
        this(socket);
        this.socket.setSoTimeout(timeout);
    }

    /**
     * Sets timeout for socket.
     * @param timeout waiting time in seconds.
     * @throws SocketException thrown to indicate that there is an error creating or accessing a Socket.
     */
    public void setSocketTimeout(int timeout) throws SocketException {
        this.socket.setSoTimeout(timeout);
    }

    /**
     * Sends message.
     * @param msg message.
     */
    public void sendMessage(@NonNull MessagePacket msg) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objOutStream = new ObjectOutputStream(outputStream);
            objOutStream.writeObject(msg);
            objOutStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Receive message.
     * @return the message for send.
     */
    public MessagePacket getMessage() {
        Object obj = null;
        try {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
            obj = objInputStream.readObject();
        } catch (Exception e) {
            e.getStackTrace();
        }
        if (obj instanceof MessagePacket) {
            return (MessagePacket) obj;
        }
        if (obj == null) throw new MessageTimeoutException("No any message.");
        throw new MessageFormatException("Get not message format.");
    }

    /**
     * Close connections.
     */
    @Override
    public void close() {
        try {
            this.socket.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * Returns whether the messenger connections are closed.
     * @return true if messenger connections are closed.
     */
    public boolean isClosed() {
        return this.socket.isClosed();
    }

}
