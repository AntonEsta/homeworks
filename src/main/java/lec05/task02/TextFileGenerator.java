package lec05.task02;

import lec05.task01.CharacterTables;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class TextFileGenerator {

//    private String textGenerator(String[] words, int size) {
    public String textGenerator() {
        final char separator = ' ';
        final char[] endOfSentence = {'.','!','?'};
        final int maxLengthOfWord = 15;
        final int maxCountOfWords = 15;
        final int maxLengthOfParagraph = 20;

        int lengthOfWord;       // длина слова
        int countOfWords;       // кол-во слов в предложении
        int countOfSentences;   // кол-во предложений в абзаце

        Random rnd = new Random();

        // Формируем текст
        StringBuilder text = new StringBuilder();
        int countOfParagraphs = 3;
        int counterParagraph = 0;
        // Формируем абзац
        while (counterParagraph < countOfParagraphs) {
            StringBuilder paragraph = new StringBuilder();
            int counterSentences = 0;
            countOfSentences = rnd.nextInt(maxLengthOfParagraph);
            while (counterSentences < countOfSentences) {

                // Формируем предложение
                StringBuilder sentence = new StringBuilder();
                countOfWords = rnd.nextInt(maxCountOfWords);
                int counterWords = 0;

                while (counterWords < countOfWords) {

                    lengthOfWord = rnd.nextInt((maxLengthOfWord - 2) + 1);
                    StringBuilder word = new StringBuilder();

                    // Формируем слово
                    while (word.length() < lengthOfWord) {
                        int i = rnd.nextInt(CharacterTables.latinLowerCaseCharArray.length - 1);
                        if (sentence.length() == 0 & word.length() == 0) {
                            word.append(CharacterTables.latinUpperCaseCharArray[i]);
                        } else {
                            word.append(CharacterTables.latinLowerCaseCharArray[i]);
                        }
                    }

                    // Добавить в предложение
                    sentence.append(word.append(separator)); /* TODO: (.|!|?) */
                    counterWords++;
                }
//                int ind = sentence.length() - 2;
                if (sentence.length() == 0) continue;
                //sentence.insert(2, '!');
                sentence.insert(sentence.length()-1, endOfSentence[rnd.nextInt(endOfSentence.length-1)]);
                        // endOfSentence[rnd.nextInt(endOfSentence.length-1)]);
                paragraph.append(sentence);
                counterSentences++;
            }
            counterParagraph++;
            paragraph.append("\n");
            text.append(paragraph);
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
