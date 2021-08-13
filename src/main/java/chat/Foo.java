package chat;

import java.util.Scanner;

public class Foo {

//    public static ChatCommand getCommandFromConsole() {
//
////        String str = getStringFromConsole();
//    }

    private String getStringFromConsole() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print(">> ");
            scanner.nextLine();
        }
        return null;
    }







    public static void main(String[] args) {

        final String PATTERN = "[\\w\\p{Punct}а-яА-Я\\s\\h]*";

//       ChatCommand cmd = Foo.getCommandFromConsole();



    }

}
