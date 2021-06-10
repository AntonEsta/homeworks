package lec05.task01.finders.classes;

import lec05.task01.data.CharacterTables;
import lec05.task01.finders.interfaces.TextFinder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class WordsFinder implements TextFinder {

    private void parse(String text, List<String> list) {
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            list.add(matcher.group().toLowerCase(Locale.ROOT));
        }
    }

    @Override
    public List<String> findAll(String text) throws PatternSyntaxException, IllegalStateException {
        List<String> list = new LinkedList<>();
        parse(text, list);
        return list;
    }

    @Override
    public Set<String> findAllUnique(String text) {
        Set<String> set = new HashSet<>();
        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            set.add(matcher.group().toLowerCase(Locale.ROOT));
        }
        return set;
    }


    @Override
    public String find() {
        return null;
    }

    @Override
    public String[] findAll() {
        return new String[0];
    }

    @Override
    public Set<String> findAllUnique() {
        return null;
    }
}
