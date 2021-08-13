package chat;

import chat.data.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import chat.data.DataBase;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class Server implements Closeable {

    final ServerSocket server;
    final DataBase userBase = new DataBase();
    ServerStatus status = ServerStatus.STOP;
    final ExecutorService executorService = Executors.newCachedThreadPool();

    private enum ServerStatus {
        STOP,
        RUNNING
    }

    public Server(int listenPort) throws IOException {
        this.server = new ServerSocket(listenPort);
    }

    public void start() throws Exception {
        System.out.println("Server started!");
//        System.out.println("Closed 1:" + this.server.isClosed());
//        System.out.println(this);


        // run looper in thread
//        this.executorService.execute(new Runnable() {
//            @SneakyThrows
//            @Override
//            public void run() {
                System.out.println(this);
                clientService();
//            }
//        });


        // run other services

        this.status = ServerStatus.RUNNING;
        System.out.println("Server started!");
        while (!executorService.isShutdown()){}
    }

    private void clientService() {
//        System.out.println(this);
//        System.out.println("Closed 2: " + this.server.isClosed());
//        this.server = new ServerSocket(this.server.getLocalPort());
        try {
            while ( !this.server.isClosed() ) {
                Socket socket = this.server.accept();
//                this.executorService.execute(new Runnable() {
//                    @SneakyThrows
//                    @Override
//                    public void run() {
//    ////                    new ClientSupport(socket);
//                    System.out.println("\n\nSupport gets client from " + socket.getRemoteSocketAddress() + socket.getPort());
//    //                    try ( ClientSupport cl = new ClientSupport(socket, userBase)) {
//                    ClientSupport cl = new ClientSupport(socket, userBase);
//                    cl.run();
//                    System.out.println("!!!");
//    //                    }
//                    System.out.println("\n\nSupport outs client from " + socket.getRemoteSocketAddress() + socket.getPort());
//
//                    }
//                });

                this.executorService.execute(new ClientSupport(socket, this.userBase));
            }
        } catch (IOException e) {
            try {
                this.server.close();
            } catch (IOException ignored) {}
            e.printStackTrace();
        }
    }


//    private void receiveManager(Socket socket, Object obj) throws IOException, ClassNotFoundException, MessageFormatException {
//
//        if ( obj instanceof MessagePacket) {
//            MessagePacket msg = ((MessagePacket) obj);
//
//            if ( msg.getMessageType().equals(MessageType.REGISTRATION_REQUEST) ) {
//                Object content = msg.getContent();
//                if ( !(content instanceof String) ) throw new MessageFormatException();
//                try {
//                    userBase.addUser((String) content);
//                } catch (UserException e) {
//                    msg = MessagePacket.newRefuseReqMessage();
//                    sendMessage(socket, msg);
//                }
//                sendMessage(socket, msg);
//            }
//        }
//
//        throw new MessageFormatException();
//
//    }

    private void sendMessage(Socket socket, MessagePacket msg) throws IOException, MessageConfirmationException {
        new ConfirmingMessenger(socket).sendMessage(msg);
    }

    private MessagePacket getMessage(Socket socket) throws IOException, ClassNotFoundException {
        return new ConfirmingMessenger(socket).getMessage();
    }


//    private boolean getAckMessage(Socket socket) throws IOException, ClassNotFoundException {
//        Object obj = getMessage(socket);
//        if (obj instanceof MessagePacket) {
//            MessagePacket msg = (MessagePacket) obj;
//            System.out.println("Поддтв пришло");
//            return msg.getMessageType().isAcknowledgment();
//        }
//        return false;
//    }

//    private boolean sendMessage(Socket socket, MessagePacket msg) throws IOException, ClassNotFoundException {
//        // send message
//        OutputStream outputStream = socket.getOutputStream();
//        ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);
////        do {
//            objOutputStream.writeObject(msg);
//            System.out.println("Сообщ отпр");
//            if ( msg.isAckMessage() ) return true;
////        } while (!getAckMessage(socket));
//        return getAckMessage(socket);
//    }

//    private void clientSupport(Socket socket) throws IOException, ClassNotFoundException {
//        ClientSupport support = new ClientSupport(socket);
//
//        Object inObj = getMessage();
//        try {
//            receiveManager(socket, inObj);
//        } catch (MessageFormatException ignored) {
//
//        }
//    }

//    private MessagePacket getMessage(Socket socket) throws IOException, ClassNotFoundException, MessageFormatException {
//        InputStream inputStream = socket.getInputStream();
//        ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
//        Object obj = objInputStream.readObject();
//        if ( obj instanceof MessagePacket ) {
//            MessagePacket msg = (MessagePacket) obj;
//            System.out.println("Получено сообщ от сервера.");
//            if (!msg.isAckMessage()) sendAckToServer(socket, msg);
//            System.out.println("Отправлено подтв.");
//        }
//        throw new MessageFormatException();
//    }

//    private void sendAckToServer(Socket socket, MessagePacket message) throws IOException, ClassNotFoundException {
//        MessagePacket msg = MessagePacket.newAcknowledgmentMessage(this.userID, message.getMessageID());
//        sendMessage(socket, msg);
//    }

    public boolean isRunning() {
        return this.status.equals(ServerStatus.RUNNING);
    }

    @Override
    public void close() throws IOException {
        this.executorService.shutdown();
        this.server.close();
        this.status = ServerStatus.STOP;
    }


    public static void main(String[] args) {
        int port = 4445;
        try ( Server serv = new Server(port) ) {
            serv.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
