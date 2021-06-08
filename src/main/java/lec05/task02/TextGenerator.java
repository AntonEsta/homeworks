package lec05.task02;

import lec05.task01.data.CharacterTables;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/*
 *  Класс - генератор текстовых файлов
 * */
@Builder
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class TextGenerator {

    final String separator;
    final char[] endOfSentence;
    final int maxLengthOfWord;
    final int maxCountOfWords;
    final int maxLengthOfParagraph;

    final Random rnd = new Random();


    private int getSomeLetter() {
        return rnd.nextInt(CharacterTables.latinLowerCaseCharArray.length - 1) + 1;
    }

    private String getSomeEnding() {
        int ch = rnd.nextInt(endOfSentence.length - 1);
        ch = endOfSentence[ch];
        return String.valueOf(ch);
    }

    /*
     * Генерация слова
     * */
    private String getWord() {
        int lengthOfWord = rnd.nextInt(maxLengthOfWord - 1) + 1;
        StringBuilder word = new StringBuilder();
        while (word.length() < lengthOfWord) {
            int i = getSomeLetter();
            word.append(CharacterTables.latinLowerCaseCharArray[i]);
        }
        return word.toString();
    }

    private String getSeparator() {
        return (rnd.nextInt(30) != 20) ? this.separator : ", ";
    }

    /*
     *  Генерация предложения
     * */
    private String getSentence() {
        StringBuilder sentence = new StringBuilder();
        int countOfWords = rnd.nextInt(maxCountOfWords - 1) + 1;
        int counterWords = 0;
        while (counterWords < countOfWords) {
            sentence.append(getWord());
            sentence.append(getSeparator());
            counterWords++;
        }
        sentence.replace(sentence.length() - 1, sentence.length(), getSomeEnding());
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
            paragraph.append(getSentence());
            paragraph.append(" ");
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
}
