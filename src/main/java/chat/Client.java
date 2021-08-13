package chat;

import chat.data.Message;
import chat.exception.CommandFormatException;
import chat.exception.MessageFormatException;
import lombok.*;
import lombok.experimental.FieldDefaults;
import chat.data.MessagePacket;
import chat.data.UserID;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client {

    volatile UserID userID;
    final int defaultPort = 4445;
    final ExecutorService executorService = Executors.newCachedThreadPool();
    final InetAddress targetAddress;
    ScheduledPeriod periodNewMsgReq = new ScheduledPeriod(5_000, TimeUnit.MILLISECONDS);
    final int port;
    final String regexCmdMessageToUser = "^[Ff]or\\s*[\\wА-яа-я\\p{Punct}]*:";

    public Client() {
        this.targetAddress = InetAddress.getLoopbackAddress();
        this.port = defaultPort;
    }

    public Client(InetAddress targetAddress, int port, ScheduledPeriod periodNewMessage) {
        this.targetAddress = (targetAddress != null) ? targetAddress : InetAddress.getLoopbackAddress();
        this.port = (port > 0) ? port : defaultPort;
        if ( periodNewMessage != null) {
            this.periodNewMsgReq = periodNewMessage;
        }
    }

    public Client(InetAddress targetAddress, int port, @NonNull UUID userID, ScheduledPeriod periodNewMessage) {
        this.targetAddress = targetAddress;
        this.userID = new UserID(userID);
        this.port = (port > 0) ? port : defaultPort;
        if ( periodNewMessage != null) {
            this.periodNewMsgReq = periodNewMessage;
        }
    }

    @SneakyThrows
    void start() {
        try {
            // регистрируем пользователя
            if ( registrationOnServer() ) {
//                startListener();
                startMessCreator();
//                startNewMessagesRequester();
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    void stop() {
//        socket.close();
        System.out.println("Connection is closed!");
    }

    private void startNewMessagesRequester() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                requestNewMessage();
            }
        }, 0, periodNewMsgReq.getPeriod(), periodNewMsgReq.getTimeUnit());
    }

    private void requestNewMessage() throws IOException, ClassNotFoundException {
        MessagePacket msg = MessagePacket.newReqNewMessage(this.userID);
        sendMessage(msg);
        try {
            msg = getMessage();
            processNewMessage(msg);
        } catch (IOException ignored) {}
    }

    private void processNewMessage(@NonNull MessagePacket msg) throws IOException {
        if (msg.isNewMessages()) {
            Message[] messages = (Message[]) msg.getContent();
            for (Message message : messages) {
                writeMessageToConsole(message);
            }
        }
    }

    private void startMessCreator() throws IOException, ClassNotFoundException {
//        executorService.execute(new Runnable() {
//            @SneakyThrows
//            @Override
//            public void run() {
                messageCreator();
//            }
//        });
    }

//    @SneakyThrows
//    private void startListener() {
////        executorService.execute(new Runnable() {
////            @SneakyThrows
////            @Override
////            public void run() {
//                listen();
////            }
////        });
//    }

    private void sendMessage(@NonNull MessagePacket msg) throws IOException, MessageConfirmationException {
        sendMessage(getDefaultSocket(), msg);
//        new ConfirmingMessenger(getDefaultSocket()).sendMessage(msg);
    }

    private void sendMessage(@NonNull Socket socket, @NonNull MessagePacket msg) throws IOException, MessageConfirmationException {
        try ( ConfirmingMessenger cm = new ConfirmingMessenger(socket)) {
            cm.sendMessage(msg);
        }
    }

    private MessagePacket getMessage() throws IOException, ClassNotFoundException {
        return getMessage(getDefaultSocket());
    }

    private MessagePacket getMessage(@NonNull Socket socket) throws IOException {
        try ( ConfirmingMessenger cm = new ConfirmingMessenger(socket)) {
            return cm.getMessage();
        }
    }

//    @SneakyThrows
    private boolean registrationOnServer() throws IOException {
        while (true) {
            String nickname = getNicknameFromConsole();
            MessagePacket msg = MessagePacket.newRegReqMessage(nickname);
            System.out.println("Registration...");

            Socket socket = getDefaultSocket();

            MessagePacket receiveMsg = null;

//            sendMessage(msg);
//            try {
//                receiveMsg = getMessage();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }

            try ( ConfirmingMessenger cm = new ConfirmingMessenger(socket)) {
                // send request
                cm.sendMessage(msg);
                // get answer
                receiveMsg = cm.getMessage();
            }

            // try get userID
            if (receiveMsg != null) {
                if (receiveMsg.isConfirmationOfRegistration()) {
                    this.userID = receiveMsg.getToUser()[0];
                    System.out.println("Registration finished! User ID: " + this.userID);
                    return true;
                }
            }
            System.out.println("Try again...");
        }
    }

    private void receiveManager(Object obj) throws IOException, MessageFormatException {
        if ( obj instanceof MessagePacket) {
            MessagePacket msg = ((MessagePacket) obj);
            if ( msg.isNewMessages() ) {
                processNewMessage(msg);
            }
        }
        throw new MessageFormatException();
    }

    private void writeMessageToConsole(@NonNull Message msg) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        String str = String.format("%s: %s", msg.getFromUser(), msg.getContent());
        writer.write(str);
        writer.close();
    }

//    private void listen() throws IOException, ClassNotFoundException {
//        //TODO возможно вывести в отдельный поток и цикл
//        Socket socket = new Socket(targetAddress, port);
//        try (ConfirmingMessenger cm = new ConfirmingMessenger(socket)) {
//            while (!cm.isClosed()) {
//                Object obj = cm.getMessage();
//                try {
//                    receiveManager(obj);
//                } catch (MessageFormatException e) {
//                    e.getStackTrace();
//                }
//            }
//        }
//    }

    private void messageCreator() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.print(">> ");
            String inputString = getStringFromConsole();
            if (inputString == null) continue;
            try {
                ChatCommand cmd = new ChatCommandParser().parse(inputString);
                doCommand(cmd);
            } catch (CommandFormatException e) {
                System.out.println("Wrong command! Please, try again.");
            }
        }
    }

    private String getStringFromConsole() {
        String inputString = null;
        // gets string
        Scanner scanner = new Scanner(System.in);
            try {
//                if (scanner.hasNextLine()) {
                    inputString = scanner.nextLine();
                    System.out.println(inputString);
//                }
            } catch (InputMismatchException e) {
                    System.out.println("Not support command.");
                    throw new RuntimeException();
            }
        return inputString;
    }

//    private void messageForUserFromConsole(String str) throws IOException, ClassNotFoundException {
//        String[] usrNicknames = getUserNameFromString(str);
//        String content = getContentFromInput(str);
//        UserID[] toUsers = getUserIDFromServer(usrNicknames);
//        sendMessageForUser(content, toUsers);
//    }

    private String getContentFromInput(@NonNull String str) {
        return str.replaceAll(regexCmdMessageToUser, "");
    }

    private void sendMessageForUser(@NonNull Object content, UserID... toUsers) throws IOException, ClassNotFoundException {
        MessagePacket msg = MessagePacket.newMessage(this.userID, toUsers, content);
//        sendMessage(msg);
        try (ConfirmingMessenger cm = new ConfirmingMessenger(getDefaultSocket())) {
            cm.sendMessage(msg);
        }
    }

    private void sendMessageForUser(@NonNull Object content, String... userNames) throws IOException, ClassNotFoundException {
        UserID[] userIDFromServer = getUserIDFromServer(userNames);
        sendMessageForUser(content, userIDFromServer);
    }


    private String[] getUserNameFromString(@NonNull String str) {
        Matcher matcher = Pattern.compile(regexCmdMessageToUser).matcher(str);
        if ( !matcher.matches() ) return null;
        return matcher.group().replaceFirst("^[Ff]or\\s*", "")
                .replaceFirst("[:,\\s]*", ":").split(":");
    }

    private UserID[] getUserIDFromServer(@NonNull String[] nicknames) throws IOException, ClassNotFoundException {
        // if ( nicknames == null ) return null;
        Collection<UserID> toUsers = new ArrayList<>();
        Socket socket = getDefaultSocket();
        try (ConfirmingMessenger cm = new ConfirmingMessenger(socket)) {
            for (String nickname : nicknames) {
                MessagePacket msg = MessagePacket.newWhoisMessage(this.userID, nickname);
                cm.sendMessage(msg);
                msg = cm.getMessage();
                if (!msg.isWhoIsMessage()) continue;
                if (msg.getFromUser() != null) toUsers.add(msg.getFromUser());
            }
        }
        return toUsers.toArray(new UserID[0]);
    }

    private Socket getDefaultSocket() throws IOException {
        int availableAttempts = 10;
        int attempt = 0;
        while (true) {
            attempt++;
            try {
                System.out.println("try connect socket " + targetAddress + ":" + port);
                return new Socket(targetAddress, port);
            } catch (IOException e) {
                if (attempt < availableAttempts) continue;
                throw e;
            }
        }
    }

    private boolean doCommand(@NonNull ChatCommand command) throws IOException, ClassNotFoundException {

        switch (command.getCommand()) {
            case MESSAGE:
                sendMessageForUser(command.getContent(), command.getArguments());
                break;
            case CLIENT:
                doClientCommand(command);
                break;
            case USER:
                doUserCommand(command);
                break;
        }
        return true;
    }

    private void doUserCommand(@NonNull ChatCommand command) throws IOException, ClassNotFoundException {
        for (String arg : command.getArguments()) {
            switch (arg.toLowerCase()) {
                // to stop client programme
                case "quite" :
                    detachUserFromServer();
                    break;
            }
        }
    }

    private void doClientCommand(@NonNull ChatCommand command) {
        for (String arg : command.getArguments()) {
            switch (arg.toLowerCase()) {
                // to stop client programme
                case "stop" : stop(); break;
            }
        }
    }

    private void detachUserFromServer() throws IOException, ClassNotFoundException {
        sendMessage(MessagePacket.newDetachUserMessage(userID));
        //TODO удалить пользователя с сервера
    }

    private String getNicknameFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write your nickname in chart");
        String nickname = "";
        try {
            nickname = scanner.next("\\w*\\s*");
        } catch (InputMismatchException e) {
            System.out.println("Nickname does not meet the criteria. Try again.");
            getNicknameFromConsole();
        }

        if ( nickname.equals("") ) return null;
        return nickname;
    }


    public static void main(String[] args) {
        Client client = new Client(null, 4445, null);
        client.start();
    }

}
