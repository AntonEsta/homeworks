package lec05.task01.finders.classes;

import lec05.task01.finders.interfaces.Finder;
import lombok.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class to search for words in a character sequence.
 * For example in the text.
 */
@EqualsAndHashCode
@ToString
public class WordFinder implements Finder<String> {

    private final Matcher matcher;

    public WordFinder(@NonNull String text) {
        String regex = "[a-zA-Zа-яА-Я]+";
        Pattern pattern = Pattern.compile(regex);
        matcher = pattern.matcher(text);
    }

    public String next() {
        if (!matcher.find()) {
            matcher.reset();
            return null;
        }
        return matcher.group();
    }

    public Collection<String> findAll() {
        Collection<String> list = new LinkedList<>();
        String word;
        while ((word = next()) != null) {
            list.add(word);
        }
        return list;
    }


    @Override
    public String find() {
        matcher.reset();
        if (!matcher.find()) return null;
        return matcher.group();
    }
}