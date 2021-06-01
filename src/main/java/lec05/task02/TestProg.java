package lec05.task02;


import java.io.IOException;
import java.util.Arrays;

public class TestProg {
    public static void main(String[] args) {

//        final String inputLorem = "/home/esta/IdeaProjects/homeworks/src/main/resources/input.txt";
//        final String outputLorem = "/home/esta/IdeaProjects/homeworks/src/main/resources/output.txt";
//        final String inputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/input-skazka-o-care-saltane.txt";
//        final String outputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/output-skazka-o-care-saltane.txt";

//        TextFileGenerator tfg = new TextFileGenerator();



        /* #1 */
        TextFileGenerator tfg = TextFileGenerator.builder()
                                        .separator(" ")
                                        .endOfSentence(new char[]{'.','!','?'})
                                        .maxLengthOfWord(15)
                                        .maxCountOfWords(15)
                                        .maxLengthOfParagraph(20)
                                        .build();

        int count = 0;
        int repeat = 100;
        while (count != repeat) {
            System.out.println("Text #" + (count + 1));
            System.out.println(tfg.getText(3));
            count++;
        }

        System.out.printf("%ngetFile():");

        /* #2 */
        String path = "/home/esta/IdeaProjects/homeworks/src/main/resources";
        String str = "Hello World! Hello World! Hello World! Hello World!";

        try {
            tfg.getFile(path, 3, 30000000, str.split(" "));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
