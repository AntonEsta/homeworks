package chat.command;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Command class
 */
public class Commander {

    /**
     * Returns chat command from console.
     * @return the chat command.
     * @throws CommandFormatException if command format is wrong.
     */
    public static ChatCommand getCommandFromConsole() throws CommandFormatException {
        String str;
        try {
            str = getStringFromConsole();
        } catch (Exception e) {
            throw new CommandFormatException();
        }
        if (str == null) {
            return null;
        }
        ChatCommandParser parser = new ChatCommandParser();
        return parser.parse(str);
    }

    /**
     * Returns string from console.
     * @return string from console.
     * @throws InputMismatchException if command not support.
     */
    private static String getStringFromConsole() throws InputMismatchException {
        String inputString;
        System.out.print(">> ");
        Scanner scanner = new Scanner(System.in);
        try {
            inputString = scanner.nextLine();
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Not support command.");
        }
        return inputString;
    }

}
