package lec05.task02;

import lombok.NonNull;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
*  Класс - генератор текстовых файлов
* */
public class TextFileGenerator {

    /*final String separator;
    final char[] endOfSentence;
    final int maxLengthOfWord;
    final int maxCountOfWords;
    final int maxLengthOfParagraph;

    final Random rnd = new Random();

    *//*
    * Генерация слова
    * *//*
    private String getWord() {
        int lengthOfWord = rnd.nextInt(maxLengthOfWord - 1) + 1;
        StringBuilder word = new StringBuilder();
        while (word.length() < lengthOfWord) {
            int i = rnd.nextInt(CharacterTables.latinLowerCaseCharArray.length - 1) + 1;
            word.append(CharacterTables.latinLowerCaseCharArray[i]);
        }
        return word.toString();
    }

    private String getSeparator() {
        return (rnd.nextInt(30) != 20) ? this.separator : ", ";
    }

    *//*
    *  Генерация предложения
    * *//*
    private String getSentence() {
        StringBuilder sentence = new StringBuilder();
        int countOfWords = rnd.nextInt(maxCountOfWords - 1) + 1;
        int counterWords = 0;
        while (counterWords < countOfWords) {
            sentence.append(getWord());
            sentence.append(getSeparator());
            counterWords++;
        }
        sentence.replace(sentence.length() - 1, sentence.length(),
                String.valueOf(endOfSentence[rnd.nextInt(endOfSentence.length - 1)]));
        sentence.replace(0, 0, String.valueOf(sentence.charAt(0)).toUpperCase());
        return sentence.toString();
    }

    *//*
     *  Генерация абзаца
     * *//*
    private String getParagraph() {
        StringBuilder paragraph = new StringBuilder();
        int counterSentences = 0;
        int countOfSentences = rnd.nextInt(maxLengthOfParagraph -1 ) + 1;
        while (counterSentences < countOfSentences) {
            paragraph.append(getSentence());
            paragraph.append(" ");
            counterSentences++;
        }
        paragraph.replace(paragraph.length() - 1, paragraph.length(), "\n");
        return paragraph.toString();
    }

    *//*
     *  Генерация текста
     * *//*
    public String getText(int countOfParagraph) {
        StringBuilder text = new StringBuilder();
        int counterParagraph = 0;
        while (counterParagraph < countOfParagraph) {
            text.append(getParagraph());
            counterParagraph++;
        }
        return text.toString();
    }*/

    private int writeStringsToFile(Path path, int fileSize, String[] words, int marker) throws IOException {

        if (marker < 0 || marker >= words.length) throw new ArrayIndexOutOfBoundsException();

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            while (Files.size(path) < fileSize) {
                bw.write(words[marker++]);
                if (marker == words.length) marker = 0;
                bw.newLine();
            }
        }
        return marker;
    }

    /*
    *   Генерация файла
    * */
    public void getFile(@NonNull String path, int n, int size, String[] words) throws IOException {
        int counterFiles = 0;
        int mark = 0;
        String fileExtension = ".txt";
        String subdir = "/out";
        Path pPath;
        while (counterFiles < n) {
            pPath = Paths.get(path, subdir +  String.valueOf(counterFiles + 1) + fileExtension);
            mark = writeStringsToFile(pPath, size, words, mark);
            counterFiles++;
        }
    }

}
