package chat;

import lombok.Data;
import lombok.NonNull;

import java.util.Arrays;

@Data
public class ChatCommand {

    private final Command command;
    private final String[] arguments;
    private final Object content;

    public static enum Command {
        MESSAGE,
        USER,
        CLIENT
    }

    public ChatCommand(@NonNull ChatCommand.Command command, Object content, @NonNull String... arguments) {
        this.command = command;
        this.arguments = Arrays.stream(arguments).filter(s->!s.isEmpty()).toArray(String[]::new);
        this.content = content;
    }

}
