package lec05.task02;


import java.io.IOException;

public class TestProg {
    public static void main(String[] args) {

        /* #1 */
        TextGenerator tg = new TextGenerator(3);

        int count = 0;
        int repeat = 100;
        while (count != repeat) {
            System.out.println("Text #" + (count + 1));
            System.out.println(tg.generate());
            count++;
        }

        System.out.printf("%n getFile():");

        /* #2 */
        String path = "/home/esta/IdeaProjects/homeworks/src/main/resources";
        String str = "Hello World! Hello World! Hello World! Hello World!";

        TextFileGenerator tfg = new TextFileGenerator();

        try {
            tfg.getFile(path, 3, 30000000, str.split(" "));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
