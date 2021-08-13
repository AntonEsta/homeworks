package chat;

import chat.data.MessagePacket;
import chat.exception.MessageFormatException;
import lombok.Getter;
import lombok.NonNull;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.UUID;

public class SimpleMessenger implements Messenger<MessagePacket>, AutoCloseable {

    private final Socket socket;
    @Getter
    private final UUID sessionID;
//    private final ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
//    private final ObjectOutputStream outputStream;

    protected SimpleMessenger(@NonNull Socket socket) throws IOException {
        if (socket.isClosed()) throw new SocketException("Socket is closed. (session ID: " + getSessionID());
        this.socket = socket;
        this.sessionID = UUID.randomUUID();
        System.out.println("\n   Start messenger session " + this.sessionID);
//        this.inputStream = new ObjectInputStream(socket.getInputStream());
//        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public void sendMessage(@NonNull MessagePacket msg) {
        // send reg. msg
        try {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objOutStream = new ObjectOutputStream(outputStream);
            System.out.println("Send message " + msg);
            objOutStream.writeObject(msg);
            objOutStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MessagePacket getMessage() {
        Object obj = null;
        try {
            InputStream inputStream = socket.getInputStream();
        // TODO костыль
//            if (inputStream.available() > 1) {
            ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
            obj = objInputStream.readObject();
        } catch (Exception e) {
            e.getStackTrace();
        }
        if (obj != null) System.out.println("Get message " + obj);
//        Objects.requireNonNull(obj, "Get empty message.");
        if (obj instanceof MessagePacket) {
            return (MessagePacket) obj;
        }
        throw new MessageFormatException("Get not message format.");
//            }
//        return null;
    }

    @Override
    public void close() {
        try {
//            InputStream inputStream = this.socket.getInputStream();
//            OutputStream outputStream = this.socket.getOutputStream();
//            this.inputStream.close();
//            this.outputStream.close();
            this.socket.close();
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("  Exception -> Close messenger session " + this.sessionID + "\n");
        }
        System.out.println("   Close messenger session " + this.sessionID + "\n");
    }

    public boolean isClosed() {
        return this.socket.isClosed();
    }

}
