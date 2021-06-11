package lec05.task01.finders.classes;

import lec05.task01.finders.interfaces.Finder;
import lombok.NonNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsFinder implements Finder<String> {

    private void parse(String text, Collection<String> collection) {
        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            collection.add(matcher.group().toLowerCase(Locale.ROOT));
        }
    }

    /*@Override
    public List<String> findAll(String text) throws PatternSyntaxException, IllegalStateException {
        List<String> list = new LinkedList<>();
        parse(text, list);
        return list;
    }*/

    /*@Override
    public Set<String> findAllUnique(String text) {
        Set<String> set = new HashSet<>();
        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            set.add(matcher.group().toLowerCase(Locale.ROOT));
        }
        return set;
    }*/


    @Override
    public String[] findAll(@NonNull String text) {
        Collection<String> list = new LinkedList<>();
        parse(text, list);
        return list.toArray(new String[0]);
    }

    @Override
    public Set<String> findAllUnique(@NonNull String text) {
        Set<String> set = new HashSet<>();
        parse(text, set);
        return set;
    }
}
