package chat.server;

import chat.command.ChatCommand;
import chat.command.Commander;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import chat.database.DataBase;

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

    public void start() {
        // run looper in thread
        this.executorService.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                clientService();
            }
        });
        // run other services
        this.status = ServerStatus.RUNNING;
        System.out.println("Server started! (for exit print \"quit\")");
        while (this.isRunning()){
            try {
                final ChatCommand chatCommand = Commander.getCommandFromConsole();
                if (chatCommand == null) continue;
                if (chatCommand.getCommand().equals(ChatCommand.Command.QUIT)) {
                    close();
                }
            } catch (Exception ignored) {}
        }
    }

    private void clientService() {
        try {
            while ( this.isRunning() && !this.server.isClosed() ) {
                Socket socket = this.server.accept();
                this.executorService.execute(new ClientSupport(socket, this.userBase));
            }
        } catch (IOException e) {
            try {
                this.server.close();
            } catch (IOException ignored) {}
            e.printStackTrace();
        }
    }


    public boolean isRunning() {
        return this.status.equals(ServerStatus.RUNNING);
    }

    @Override
    public void close() throws IOException {
        this.executorService.shutdown();
        this.server.close();
        this.status = ServerStatus.STOP;
        System.exit(0);
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
