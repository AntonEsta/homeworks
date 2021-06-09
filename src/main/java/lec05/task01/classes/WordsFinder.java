package lec05.task01.classes;

import lec05.task01.data.CharacterTables;
import lec05.task01.interfaces.TextFinder;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsFinder implements TextFinder {

    private boolean isLetter(char ch) {
        return Arrays.binarySearch(CharacterTables.latinLettersCharArray, ch) >= 0 ||
                Arrays.binarySearch(CharacterTables.cyrillicLettersCharArray, ch) >= 0;
    }

    @Override
    public Collection<String> findAll(String text) {
        LinkedList<String> list = new LinkedList<>();
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            list.add(matcher.group().toLowerCase(Locale.ROOT));
        }
        return list;
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
