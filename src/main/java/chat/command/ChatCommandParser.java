package chat.command;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser commands for inner use.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatCommandParser {

    /**
     * Inner service class of regular expressions.
     */
    @FieldDefaults(level = AccessLevel.PUBLIC)
    private static class CommandRegex {

        static final String HELP;
        static final String QUITE;
        static final String DELIMITER;
        static final String SOME_TEXT;
        static final String USER_NAME;

        static final String MESSAGE_COMMAND;
        static final String MESSAGE_HEAD;
        static final String MESSAGE;

        static final String USER_COMMAND;
        static final String USER_HEAD;
        static final String CLIENT_COMMAND;
        static final String CLIENT_HEAD;

        static {
                HELP            = "^\\s*[Hh][Ee][Ll][Pp]\\s*$" ;
                QUITE           = "^\\s*[Qq][Uu][Ii][Tt]\\s*$" ;
                DELIMITER       = ":" ;
                SOME_TEXT       = "[\\w\\sа-яА-Я]*" ;
                USER_NAME       = "[\\wа-яА_Я]*";
                MESSAGE_COMMAND = "(([Ff][Oo][Rr])|([Tt][Oo]))\\s+" ;
                MESSAGE_HEAD    = "^\\s*" + MESSAGE_COMMAND + "(.*)" + DELIMITER ;
                MESSAGE         = MESSAGE_HEAD + "(.*)" ;
                USER_COMMAND    = "[Uu][Ss][Ee][Rr]\\s+" ;
                USER_HEAD       = "^\\s*" + USER_COMMAND + "().*" ;
                CLIENT_COMMAND  = "[Cc][Ll][Ii][Ee][Nn][Tt]\\s+" ;
                CLIENT_HEAD     = "^\\s*" + CLIENT_COMMAND + "().*" ;
            }
    }

    /**
     * Returns true if string contains new message command.
     * @param str the string for parse
     * @return true if string contains new message command
     */
    public static boolean isMessage(@NonNull String str) {
        return str.matches(CommandRegex.MESSAGE);
    }

    /**
     * Returns true if string contains to quit command.
     * @param str the string for parse.
     * @return true if string contains to quit command.
     */
    public static boolean isQuit(@NonNull String str) {
        return str.matches(CommandRegex.QUITE);
    }

    /**
     * Returns true if string contains command to client.
     * @param str the string for parse.
     * @return true if string contains command to client.
     */
    public static boolean isClient(@NonNull String str) {
        return str.matches(CommandRegex.CLIENT_HEAD);
    }

    /**
     * Returns true if string contains command of user.
     * @param str the string for parse.
     * @return true if string contains command of user.
     */
    private boolean isUser(@NonNull String str) {
        return str.matches(CommandRegex.USER_HEAD);
    }

    /**
     * Returns true if string contains help command.
     * @param str the string for parse.
     * @return true if string contains help command.
     */
    private boolean isHelp(String str) {
        return str.matches(CommandRegex.HELP);
    }

    /**
     * Parsing command string.
     * @param str the command string.
     * @return chat command {@link ChatCommand}.
     * @throws CommandFormatException thrown when the command string is wrong.
     */
    public ChatCommand parse(@NonNull String str) throws CommandFormatException {
        // new message
        if (isMessage(str)) {
            return parseMessageCommand(str);
        }
        // command to client
        if (isClient(str)) {
            return parseClientCommand(str);
        }
        // command for user
        if (isUser(str)) {
            return parseUserCommand(str);
        }
        // command to quit
        if (isQuit(str)) {
            return quitCommand();
        }
        // command to quit
        if (isHelp(str)) {
            return helpCommand();
        }

        throw new CommandFormatException();
    }

    /**
     * Returns chat command to help.
     * @return help chat command.
     */
    private ChatCommand helpCommand() {
        return new ChatCommand(ChatCommand.Command.HELP, null);
    }

    /**
     * Returns chat command to quit.
     * @return chat command to quit.
     */
    private ChatCommand quitCommand() {
        return new ChatCommand(ChatCommand.Command.QUIT, null);
    }

    /**
     * Parsings command for user.
     * @param str the string for parse.
     * @return the chat command.
     */
    private ChatCommand parseUserCommand(@NonNull String str) {
        if (!isUser(str)) throw new CommandFormatException();
        String cmdHead = getCommandHead(str, CommandRegex.USER_HEAD);
        if (cmdHead == null) throw new CommandFormatException();
        String[] args = getCommandArgs(cmdHead, CommandRegex.USER_COMMAND);
        for (String s : args) {
            System.out.println(s);
        }
        return new ChatCommand(ChatCommand.Command.USER, null, args);
    }

    /**
     * Parsings command of new message.
     * @param str the string for parse.
     * @return the chat command.
     */
    private ChatCommand parseMessageCommand(@NonNull String str) throws CommandFormatException {
        if (!isMessage(str)) throw new CommandFormatException();
        String cmdHead = getCommandHead(str, CommandRegex.MESSAGE_HEAD);
        if (cmdHead == null) throw new CommandFormatException();
        String[] userNames = getCommandArgs(cmdHead, CommandRegex.MESSAGE_COMMAND);
        String messageBody = str.replace(cmdHead, "").trim();
        return new ChatCommand(ChatCommand.Command.MESSAGE, messageBody, userNames);
    }

    /**
     * Parsings command for client.
     * @param str the string for parse.
     * @return the chat command.
     */
    private ChatCommand parseClientCommand(@NonNull String str) {
        if (!isClient(str)) throw new CommandFormatException();
        String cmdHead = getCommandHead(str, CommandRegex.CLIENT_HEAD);
        if (cmdHead == null) throw new CommandFormatException();
        String[] args = getCommandArgs(cmdHead, CommandRegex.CLIENT_COMMAND);
        return new ChatCommand(ChatCommand.Command.CLIENT, null, args);
    }

    /**
     * Parsings command arguments.
     * @param str the string for parse.
     * @return array of arguments.
     */
    private String[] getCommandArgs(@NonNull String str, @NonNull String commandRegex) {
        String regex = "(^" + commandRegex + ")|(\\s+" + commandRegex + ")";
        String subStr = str.replaceAll(regex, "");
        subStr = subStr.replace(CommandRegex.DELIMITER, "");
        return subStr.split("(\\s*[,;]\\s*)|\\s+");
    }

    /**
     * Parsings command head.
     * @param str the string for parse.
     * @param commandRegex the command head regular expression.
     * @return the command head.
     */
    private String getCommandHead(@NonNull String str, @NonNull String commandRegex) {
        Matcher matcher = Pattern.compile(commandRegex).matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

}
