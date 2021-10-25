package chat.client;

import chat.command.ChatCommand;
import chat.command.ChatCommandParser;
import chat.command.Commander;
import chat.message.Message;
import chat.command.CommandFormatException;
import chat.message.MessageFormatException;
import chat.message.MessageTimeoutException;
import chat.exception.OperationException;
import chat.messanger.ConfirmingMessenger;
import chat.messanger.MessageConfirmationException;
import chat.messanger.ScheduledPeriod;
import lombok.*;
import lombok.experimental.FieldDefaults;
import chat.message.MessagePacket;
import chat.user.UserID;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Chat client class.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client {

    // user ID
    volatile UserID userID;
    // default server port to connect
    final int defaultPort = 4445;
    final ExecutorService executorService = Executors.newCachedThreadPool();
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    final InetAddress targetAddress;
    // new messages request period in seconds
    final int newMessageReqPeriod = 1_000;
    // scheduler for new messages request
    ScheduledPeriod newMessageReqScheduler = new ScheduledPeriod(newMessageReqPeriod, TimeUnit.MILLISECONDS);
    final int port;
    // indicate running client or not
    boolean running;

    public Client() {
        this.targetAddress = InetAddress.getLoopbackAddress();
        this.port = defaultPort;
    }

    public Client(InetAddress targetAddress, int port, ScheduledPeriod periodNewMessage) {
        this.targetAddress = (targetAddress != null) ? targetAddress : InetAddress.getLoopbackAddress();
        this.port = (port > 0) ? port : defaultPort;
        if ( periodNewMessage != null) {
            this.newMessageReqScheduler = periodNewMessage;
        }
    }

    public Client(InetAddress targetAddress, int port, @NonNull UUID userID, ScheduledPeriod periodNewMessage) {
        this.targetAddress = targetAddress;
        this.userID = new UserID(userID);
        this.port = (port > 0) ? port : defaultPort;
        if ( periodNewMessage != null) {
            this.newMessageReqScheduler = periodNewMessage;
        }
    }

    /**
     * Starts chat client.
     */
    @SneakyThrows
    public void start() {
        this.running = true;
        registrationOnServer();
        startNewMessagesRequest();
        startCommandLoop();
    }

    /**
     * Sleeps chat client.
     */
    private void sleep() {
        this.running = false;
    }

    /**
     * Stops chat client.
     */
    void stop() {
        System.out.print("\rStopping client...");
        this.executorService.shutdown();
        this.scheduler.shutdown();
        this.running = false;
        System.out.println("Bye!");
        System.exit(0);
    }

    /**
     * Starts periodic new message requester.
     */
    private void startNewMessagesRequest() {
        this.scheduler.scheduleAtFixedRate(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                requestNewMessage();
            }
        }, 0, newMessageReqScheduler.getPeriod(), newMessageReqScheduler.getTimeUnit());
    }

    /**
     * Requests new message from server.
     */
    private void requestNewMessage() throws IOException {
        if (!isRegisteredUser() && !running) return;
        MessagePacket msg = MessagePacket.newReqNewMessage(this.userID);
        try (ConfirmingMessenger cm = new ConfirmingMessenger(getDefaultSocket())) {
            cm.sendMessage(msg);
            try {
                msg = cm.getMessage();
                processNewMessage(msg);
            } catch (IOException ignored) {}
        }
    }

    /**
     * Process the new receive messages.
     * @param msg the receive message.
     */
    private void processNewMessage(@NonNull MessagePacket msg) throws IOException {
        if (msg.isNewMessages()) {
            Message[] messages = (Message[]) msg.getContent();
            for (Message message : messages) {
                UserID fromUserID;
                String fromUserName;
                try {
                    fromUserID = message.getFromUser();
                    fromUserName = getUserNameFromServer(fromUserID);
                } catch (MessageTimeoutException | MessageFormatException e) {
                    continue;
                }
                if (!(message.getContent() instanceof Message)) throw new MessageFormatException("Is not Message.");
                final Message messageContent = (Message) message.getContent();
                final String msgContent = (String) messageContent.getContent();
                System.out.printf("\r%s: %s\n>> ", fromUserName, msgContent);
//                writeMessageToConsole(fromUserName, msg.getContent());
            }
        }
    }

    /**
     * Starts creator of messages from console input.
     */
    private void startCommandLoop() {
        executorService.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                commandLoop();
            }
        });
    }

    /**
     * Looper for command input.
     * @throws IOException any input exception.
     */
    private void commandLoop() throws IOException {
        while (true) {
            ChatCommand cmd = getInputCommand();
            try {
                doCommand(cmd);
            } catch (CommandFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Registrations the new user to server and returns true if operation is successful.
     */
    private void registrationOnServer() throws IOException {
        if (isRegisteredUser()) return;
        while (true) {
            String nickname = getNicknameFromConsole();
            if (nickname == null) continue;
            ChatCommandParser parser = new ChatCommandParser();
            ChatCommand command = null;
            try {
                command = parser.parse(nickname);
            } catch (CommandFormatException ignored) { }
            if (command != null) {
                if (command.getCommand().equals(ChatCommand.Command.QUIT)) {
                    stop();
                }
                continue;
            }
            MessagePacket msg = MessagePacket.newRegReqMessage(nickname);
            System.out.print("\rRegistration...");
            MessagePacket receiveMsg;
            try ( ConfirmingMessenger cm = new ConfirmingMessenger(getDefaultSocket())) {
                // send request
                cm.sendMessage(msg);
                // get answer
                receiveMsg = cm.getMessage();
            } catch (ConnectException e) {
                System.out.println("\rNo connection with server.");
                continue;
            }
            // try get userID
            if (receiveMsg != null) {
                if (receiveMsg.isConfirmationOfRegistration()) {
                    this.userID = receiveMsg.getToUser()[0];
                    System.out.print("\rRegistration successful!\n");
                    return;
                }
            }
            System.out.println("Try again...");
        }
    }

    /**
     * Returns nickname of the user by user ID.
     * @param userID the user ID whose nickname will be get.
     * @return the user nickname
     * @throws MessageTimeoutException throw if timed out waiting for response.
     * @throws MessageFormatException throw if the response is not format of message.
     */
    private String getUserNameFromServer(UserID userID) throws IOException, MessageTimeoutException, MessageFormatException {
        MessagePacket msg = MessagePacket.newWhoisMessage(userID, null);
        try (ConfirmingMessenger cm = new ConfirmingMessenger(getDefaultSocket())) {
            cm.sendMessage(msg);
            msg = cm.getMessage();
        } catch (MessageConfirmationException | MessageFormatException e) {
            throw new OperationException("Can't get nickname for user. ", e);
        }
        if (msg.isWhoIsMessage()) {
            return (String) msg.getContent();
        }
        throw new MessageFormatException("Is not user name message.");
    }

    /**
     * Gets commands from console.
     */
    private ChatCommand getInputCommand() {
        while (true) {
            try {
                ChatCommand cmd = Commander.getCommandFromConsole();
                if (cmd == null) continue;
                return cmd;
            } catch (CommandFormatException e) {
                System.out.println("Wrong command! Please, try again.");
            }
        }
    }

    /**
     * Sends message for user(s) by user ID.
     * @param content the message content.
     * @param toUsers user IDs.
     */
    private void sendMessageForUser(@NonNull Object content, UserID... toUsers) throws IOException {
        if (!isRegisteredUser()) return;
        MessagePacket msg = MessagePacket.newMessage(this.userID, toUsers, content);
        try (ConfirmingMessenger cm = new ConfirmingMessenger(getDefaultSocket())) {
            cm.sendMessage(msg);
        }
    }

    /**
     * Sends message for user(s) by user nickname.
     * @param content the message content.
     * @param userNames user nicknames.
     */
    private void sendMessageForUser(@NonNull Object content, String... userNames) throws IOException {
        if (!isRegisteredUser()) return;
        UserID[] userIDFromServer;
        try {
            userIDFromServer = getUserIDFromServer(userNames);
        } catch (MessageTimeoutException e) {
            return;
        }
        sendMessageForUser(content, userIDFromServer);
    }

    /**
     * Returns whether the user is registered on the server or not.
     * @return true if the user is registered on the server.
     */
    private boolean isRegisteredUser() {
        return userID != null;
    }

    /**
     * Gets user IDs from server by their nicknames.
     * @param nicknames nicknames of users.
     * @return user IDs.
     */
    private UserID[] getUserIDFromServer(@NonNull String[] nicknames) throws IOException {
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
        } catch (MessageFormatException e) {
            throw new OperationException("Can't get user ID from server.", e);
        }
        return toUsers.toArray(new UserID[0]);
    }

    /**
     * Returns default socket for connection to server.
     * @return socket for connection to server
     */
    private Socket getDefaultSocket() throws IOException {
        int availableAttempts = 10;
        int attempt = 0;
        while (true) {
            attempt++;
            try {
                final Socket socket = new Socket(targetAddress, port);
                int socketTimeout = 10_000;
                socket.setSoTimeout(socketTimeout);
                return socket;
            } catch (IOException e) {
                if (attempt < availableAttempts) continue;
                throw e;
            }
        }
    }

    /**
     * Does chat inner commands.
     * @param command the chat command.
     * @throws OperationException throws if command is unknown.
     */
    private void doCommand(@NonNull ChatCommand command) throws IOException, OperationException, CommandFormatException {
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
            case HELP:
                doHelpCommand();
                break;
            default: throw new OperationException("Unknown command.");
        }
    }

    /**
     * Prints command help.
     */
    private void doHelpCommand() {
        System.out.println("\rTo send a message use the commands \"to\" or \"for\":");
        System.out.println("to [client]: <message>");
        System.out.println("  or  ");
        System.out.println("for [client]: <message>");
        System.out.println("to send message for all use <for | to> : <message>");
        System.out.println();
        System.out.println("For exit programme write \"client quit\"");
    }

    /**
     * Does user commands.
     * @param command chat command.
     */
    private void doUserCommand(@NonNull ChatCommand command) throws IOException, CommandFormatException {
        for (String arg : command.getArguments()) {
            switch (arg.toLowerCase()) {
                case "reg" :
                    System.out.println("registrationOnServer");
                    registrationOnServer();
                    break;
                case "quit" :
                    detachUserFromServer();
                    sleep();
                    break;
                default: throw new CommandFormatException("Argument \"" + arg + "\" not valid for this command.");
            }
        }
        throw new CommandFormatException("No found any argument for this command.");
    }

    /**
     * Does client commands.
     * @param command chat command.
     */
    private void doClientCommand(@NonNull ChatCommand command) throws CommandFormatException {
        for (String arg : command.getArguments()) {
            switch (arg.toLowerCase()) {
                // to stop client programme
                case "quit" : stop(); break;
                default: throw new CommandFormatException("Argument \"" + arg + "\" not valid for this command.");
            }
        }
        throw new CommandFormatException("No found any argument for this command.");
    }

    /**
     * Detach user from server.
     */
    private void detachUserFromServer() throws IOException {
        if (!isRegisteredUser()) return;
        try (ConfirmingMessenger cm = new ConfirmingMessenger(getDefaultSocket())) {
            cm.sendMessage(MessagePacket.newDetachUserMessage(this.userID));
        } catch (MessageConfirmationException e) {
            return;
        }
        this.userID = null;
        System.out.println("\rYou detach from server.\n (for new registration insert command \"user reg\" or \"client quit\" for exit)");
    }

    /**
     * Gets user nickname from console.
     * @return the user nickname.
     */
    private String getNicknameFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write your nickname in chart (or \"quit\" for exit)");
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
