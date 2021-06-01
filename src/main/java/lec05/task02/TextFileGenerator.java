package lec05.task02;

import lec05.task01.CharacterTables;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Random;


/*
*  Класс - генератор текстовых файлов
* */
@Builder
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class TextFileGenerator {

    final String separator;
    final char[] endOfSentence;
    final int maxLengthOfWord;
    final int maxCountOfWords;
    final int maxLengthOfParagraph;

    final Random rnd = new Random();

   /* public TextFileGenerator() {
        separator = " ";
        endOfSentence = new char[]{'.','!','?'};
        maxLengthOfWord = 15;
        maxCountOfWords = 15;
        maxLengthOfParagraph = 20;
    }*/

    /*
    * Генерация слова
    * */
    private String getWord() {
        int lengthOfWord = rnd.nextInt(maxLengthOfWord - 1) + 1;
        StringBuilder word = new StringBuilder();
        while (word.length() < lengthOfWord) {
            int i = rnd.nextInt(CharacterTables.latinLowerCaseCharArray.length - 1) + 1;
            word.append(CharacterTables.latinLowerCaseCharArray[i]);
        }
        return word.toString();
    }

    /*
    *  Генерация предложения
    * */
    private String getSentence() {
        StringBuilder sentence = new StringBuilder();
        int countOfWords = rnd.nextInt(maxCountOfWords - 1) + 1;
        int counterWords = 0;
        boolean firstUpperCase;
        while (counterWords < countOfWords) {
            String separator = (rnd.nextInt(30) != 20) ? this.separator : ", ";
            sentence.append(getWord()).append(separator);
            counterWords++;
        }
        sentence.replace(sentence.length() - 1, sentence.length(),
                String.valueOf(endOfSentence[rnd.nextInt(endOfSentence.length - 1)]));
        sentence.replace(0, 0, String.valueOf(sentence.charAt(0)).toUpperCase());
        return sentence.toString();
    }

    /*
     *  Генерация абзаца
     * */
    private String getParagraph() {
        StringBuilder paragraph = new StringBuilder();
        int counterSentences = 0;
        int countOfSentences = rnd.nextInt(maxLengthOfParagraph -1 ) + 1;
        while (counterSentences < countOfSentences) {
            paragraph.append(getSentence()).append(" ");
            counterSentences++;
        }
        paragraph.replace(paragraph.length() - 1, paragraph.length(), "\n");
        return paragraph.toString();
    }

    /*
     *  Генерация текста
     * */
    public String getText(int countOfParagraph) {
        StringBuilder text = new StringBuilder();
        int counterParagraph = 0;
        while (counterParagraph < countOfParagraph) {
            text.append(getParagraph());
            counterParagraph++;
        }
        return text.toString();
    }


    /*
    *   Генерация файла
    * */
    public void getFile(String path, int n, int size, String[] words) throws IOException {
        int counterFiles = 0;
        int mark = 0;
        Path filePath;
        while (counterFiles < n) {
            filePath = Paths.get(path + "/out" + (counterFiles + 1) + ".txt");
            try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
                while (Files.size(filePath) < size | mark < words.length) {
                    if (mark == words.length) mark = 0;
                    bw.write(words[mark++]);
                    bw.newLine();
                }
            }
            counterFiles++;
        }
    }

}
