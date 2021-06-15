package lec05.task01.finders.classes;

import lec05.task01.finders.interfaces.Finder;
import lombok.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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

    public void reset() {
        matcher.reset();
    }

    public Stream<String> findAll() {
        LinkedList<String> list = new LinkedList<>();
        String word;
        while ((word = next()) != null) {
            list.add(word);
        }
        return list.stream();
    }

    @Override
    public String find() {
        matcher.reset();
        if (!matcher.find()) return null;
        return matcher.group();
    }

    /*TODO: The method below is to be removed.*/
    public static void main(String[] args) {
        WordFinder wf = new WordFinder("Hello World!!!");
        System.out.println("One: " + wf.find());
        System.out.println("All: ");
        wf.reset();
        String str;
        while ((str = wf.next()) != null) {
            System.out.println(str);
        }
        wf = new WordFinder("Helloooo Worldddd!!!");
        System.out.println("second");
        while ((str = wf.next()) != null) {
            System.out.println(str);
        }

        System.out.println(wf.findAll());

    }

}
