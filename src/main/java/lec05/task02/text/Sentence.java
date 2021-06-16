package lec05.task02.text;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EqualsAndHashCode
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Sentence {

    final LinkedList<String> words = new LinkedList<>();

    public boolean append(@NonNull String str) {
        return words.add(str);
    }

    public int size() {
        long filteredSize = words.stream()
                .filter(Sentence::isWord)
                .count();
        return (int) filteredSize;
    }

    public String set(int index, String word) {
        return words.set(index, word);
    }

    public String get(int index) {
        return words.get(index);
    }

    public String replace(int index, int start, int end, String str) {
        StringBuilder sb = new StringBuilder(words.get(index));
        sb.replace(start, end, str);
        return set(index, sb.toString());
    }

    private static boolean isWord(String str) {
        Matcher matcher = Pattern.compile("[A-Za-zА-Яа-я]+").matcher(str);
        return matcher.find();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            if (i == 0 || !isWord(words.get(i))) {
                sb.append(words.get(i));
            } else {
                sb.append(" ").append(words.get(i));
            }
        }
        return sb.toString();
    }
}