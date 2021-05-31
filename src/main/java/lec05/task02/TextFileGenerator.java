package lec05.task02;

import lec05.task01.CharacterTables;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class TextFileGenerator {

    final char separator = ' ';
    final char[] endOfSentence = {'.','!','?'};
    final int maxLengthOfWord = 15;
    final int maxCountOfWords = 15;
    final int maxLengthOfParagraph = 20;

    Random rnd = new Random();

    private String getWord(int maxLengthOfWord, boolean firstUpperCase) {
//        StringBuilder buffer = new StringBuilder();
        int lengthOfWord = rnd.nextInt(maxLengthOfWord);
        StringBuilder word = new StringBuilder();
        // Формируем слово
        while (word.length() < lengthOfWord) {
            int i = rnd.nextInt((CharacterTables.latinLowerCaseCharArray.length - 2) + 1);
            if (i == 0) System.out.println("!!! zero !!!");
            if (firstUpperCase) {
                word.append(CharacterTables.latinUpperCaseCharArray[i]);
                firstUpperCase = false;
            } else {
                word.append(CharacterTables.latinLowerCaseCharArray[i]);
            }
        }
        return word.toString();
    }


    private String getSentence() {
        StringBuilder sentence = new StringBuilder();
        // Формируем предложение
        int countOfWords = rnd.nextInt(maxCountOfWords);
        int counterWords = 0;
        boolean firstUpperCase;
        while (counterWords < countOfWords) {
            // Добавить в предложение
            firstUpperCase = sentence.length() == 0;
            sentence.append(getWord(maxLengthOfWord, firstUpperCase) + separator); /* TODO: (.|!|?) */
            counterWords++;
        }
        if (sentence.length() != 0) {
            sentence.append(sentence.insert(sentence.length() - 1, endOfSentence[rnd.nextInt(endOfSentence.length - 1)]));
        }
        return sentence.toString();
    }

    private String getParagraph() {
        // Формируем абзац
        StringBuilder paragraph = new StringBuilder();
        int counterSentences = 0;
        int countOfSentences = rnd.nextInt(maxLengthOfParagraph);
        while (counterSentences < countOfSentences) {
            paragraph.append(getSentence() + " ");
            counterSentences++;
        }
        paragraph.append("\n");
        return paragraph.toString();
    }

//    private String textGenerator(String[] words, int size) {
    public String textGenerator(int countOfParagraph) {

//        int lengthOfWord;       // длина слова
//        int countOfWords;       // кол-во слов в предложении
//        int countOfSentences;   // кол-во предложений в абзаце

        // Формируем текст
        StringBuilder text = new StringBuilder();
//        int countOfParagraphs = 3;
        int counterParagraph = 0;

        while (counterParagraph < countOfParagraph) {
            text.append(getParagraph());
            counterParagraph++;
        }
        return text.toString();
    }

    public void getFile(String path, int n, int size, String[] words) throws IOException {

        Byte[] buffer = new Byte[n];


        //while buffer.length

       /* try {
            Path filePath = Paths.get(path);
            *//*Получить буфер для записи в файл по размеру *//*
            // Files.write(filePath, Arrays.copyOfRange(words, s, n));

            // Files.write(Paths.get(path), textGenerator(words, size).getBytes(StandardCharsets.UTF_8));
            *//*TODO: Продолжить здесь!*//*

        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

}
