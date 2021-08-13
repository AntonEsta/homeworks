package chat;

import chat.exception.CommandFormatException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatCommandParser {

    @FieldDefaults(level = AccessLevel.PUBLIC)
    private static class CommandRegex {
        final static String DELIMITER;
        final static String SOME_TEXT;
        final static String USER_NAME;

        final static String MESSAGE_COMMAND;
        final static String MESSAGE_HEAD;
        final static String MESSAGE;

        final static String USER_COMMAND;
        final static String USER_HEAD;
        static final String CLIENT_COMMAND;
        static final String CLIENT_HEAD;

        static {
                DELIMITER       = ":" ;
                SOME_TEXT       = "[\\w\\sа-яА-Я]*" ;
                USER_NAME       = "[\\wа-яА_Я]*";
                MESSAGE_COMMAND = "(([Ff][Oo][Rr])|([Tt][Oo]))\\s+" ;
                MESSAGE_HEAD    = "^\\s*" + MESSAGE_COMMAND + "(.*)" + DELIMITER ;
                MESSAGE         = MESSAGE_HEAD + "(.*)" ;
    //            MESSAGE_HEAD    = MESSAGE + SOME_TEXT + ":" ;
                USER_COMMAND    = "[Uu][Ss][Ee][Rr]\\s+" ;
                USER_HEAD       = "^\\s*" + USER_COMMAND + "().*" ;
                CLIENT_COMMAND  = "[Cc][Ll][Ii][Ee][Nn][Tt]\\s+" ;
                CLIENT_HEAD     = "^\\s*" + CLIENT_COMMAND + "().*" ;
            }
    }

    public static boolean isMessage(@NonNull String str) {
        return str.matches(CommandRegex.MESSAGE);
    }

    public static boolean isClient(@NonNull String str) {
        return str.matches(CommandRegex.CLIENT_HEAD);
    }

    private boolean isUser(@NonNull String str) {
        return str.matches(CommandRegex.USER_HEAD);
    }


    public ChatCommand parse(@NonNull String str) throws CommandFormatException {
        if (isMessage(str)) {
            return parseMessageCommand(str);
        }
        if (isClient(str)) {
            return parseClientCommand(str);
        }
        if (isUser(str)) {
            return parseUserCommand(str);
        }
        throw new CommandFormatException();
    }

    private ChatCommand parseUserCommand(@NonNull String str) {
        if (!isUser(str)) throw new CommandFormatException();

        System.out.println("run parseUserCommand()");

        String cmdHead = getCommandHead(str, CommandRegex.USER_HEAD);
        if (cmdHead == null) throw new CommandFormatException();
        String[] args = getCommandArgs(cmdHead, CommandRegex.USER_COMMAND);

        System.out.println("args:");
        Arrays.stream(args).forEach(System.out::println);

        return new ChatCommand(ChatCommand.Command.USER, null, args);
    }

    private ChatCommand parseMessageCommand(@NonNull String str) throws CommandFormatException {
        if (!isMessage(str)) throw new CommandFormatException();
        System.out.println("run parseMessageCommand()");
        String cmdHead = getCommandHead(str, CommandRegex.MESSAGE_HEAD);
        if (cmdHead == null) throw new CommandFormatException();
        String[] userNames = getCommandArgs(cmdHead, CommandRegex.MESSAGE_COMMAND);
        System.out.println("users:");
        Arrays.stream(userNames).forEach(System.out::println);
        String messageBody = str.replace(cmdHead, "").trim();
        return new ChatCommand(ChatCommand.Command.MESSAGE, messageBody, userNames);
    }

    private ChatCommand parseClientCommand(@NonNull String str) {
        if (!isClient(str)) throw new CommandFormatException();

            System.out.println("run parseClientCommand()");

        String cmdHead = getCommandHead(str, CommandRegex.CLIENT_HEAD);
        if (cmdHead == null) throw new CommandFormatException();
        String[] args = getCommandArgs(cmdHead, CommandRegex.CLIENT_COMMAND);

            System.out.println("args:");
            Arrays.stream(args).forEach(System.out::println);

        return new ChatCommand(ChatCommand.Command.CLIENT, null, args);
    }

    private String[] getCommandArgs(@NonNull String str, @NonNull String commandRegex) {
        String regex = "(^" + commandRegex + ")|(\\s+" + commandRegex + ")";
        String subStr = str.replaceAll(regex, "");
        subStr = subStr.replace(CommandRegex.DELIMITER, "");

            System.out.println("getCommandArgs -> " + subStr);

        return subStr.split("(\\s*[,;]\\s*)|\\s+");
    }

    private String getCommandHead(@NonNull String str, @NonNull String commandRegex) {
        Matcher matcher = Pattern.compile(commandRegex).matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }


    public static void main(String[] args) {

        ChatCommandParser parser = new ChatCommandParser();

        String str = "For all, s, df; fsdf efde ewsd : kmwlkmecedelk lk mkm km lkm m jkl ml omdo mdkm om ";
//        System.out.println(str.matches("For(.*):(.*)"))
        System.out.println(parser.parse(str));
        str = "client stop jk";
        System.out.println(parser.parse(str));

    }

}
