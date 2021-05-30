package lec05.task02;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextFileGenerator {

    private String textGenerator(String[] words, int size) {
        final char separator = ' ';
        final char[] endOfSentence = {'.','!','?'};
        final int maxLengthOfWord = 15;
        final int maxLengthOfSentence = 15;
        final int maxLengthOfParagraph = 20;

        StringBuffer buffer = new StringBuffer(size);


        return buffer.toString();
    }

    public void getFile(String path, int n, int size, String[] words) throws IOException {

        try {

            Files.write(Paths.get(path), textGenerator(words, size).getBytes(StandardCharsets.UTF_8));
            /*TODO: Продолжить здесь!*/

        }

    }

}
