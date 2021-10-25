package chat.command;

import lombok.Data;
import lombok.NonNull;

import java.util.Arrays;

/**
 * Class of command for inner use.
 */
@Data
public class ChatCommand {

    private final Command command;
    private final String[] arguments;
    private final Object content;

    public enum Command {
        MESSAGE,
        USER,
        CLIENT,
        QUIT,
        HELP
    }

    public ChatCommand(@NonNull ChatCommand.Command command, Object content, @NonNull String... arguments) {
        this.command = command;
        this.arguments = Arrays.stream(arguments).filter(s->!s.isEmpty()).toArray(String[]::new);
        this.content = content;
    }

}
