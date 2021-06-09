package lec05.task01.classes;

import lec05.task01.data.CharacterTables;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class FileWordsFinder extends WordsFinder {

    private boolean isLetter(char ch) {
        return Arrays.binarySearch(CharacterTables.latinLettersCharArray, ch) >= 0 ||
                Arrays.binarySearch(CharacterTables.cyrillicLettersCharArray, ch) >= 0;
    }

    @Override
    public Collection<String> findAll(String fileName) {

        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(fileName);
             BufferedReader br = new BufferedReader(reader)) {
            int ch;
            while ((ch = br.read()) != -1) {
                sb.append(ch);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return super.findAll(sb.toString());

        /*Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(text);
        System.out.println(matcher.find());*/

    }

    /*public void find() {
//        return null;

        Collection<String> words = new HashSet<>();
        try (FileReader reader = new FileReader(inputFileName);
             BufferedReader br = new BufferedReader(reader)) {
            StringBuilder str = new StringBuilder();
            int ch;
            while ((ch = br.read()) != -1) {
                if (isLetter((char) ch)) {
                    str.append((char) ch);
                } else {
                    if (str.toString().isEmpty()) continue;
//                    foundWords++;
                    words.add(str.toString().toLowerCase());
                    str = new StringBuilder();
                }
            }
        }
        return (T) words;
    }*/

}
