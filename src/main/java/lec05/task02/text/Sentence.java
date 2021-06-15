package lec05.task02.text;

import lombok.Data;
import lombok.NonNull;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Data
public class Sentence {

    private LinkedList<String> words = new LinkedList<>();

    public boolean append(@NonNull String str) {
        return words.add(str);
    }

    public int size() {
        Stream<String> stream = words.stream().filter(this::isWord);
        return ((int) stream.count());
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

    /*TODO: delete*/
/*    public boolean insert(int index, String str) {
        try {
            words.add(index, str);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }*/

    private boolean isWord(String str) {
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