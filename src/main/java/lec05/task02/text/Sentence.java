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

    /** Appends new string to end the collection
     * @param str String for appending.
     * */
    public void append(@NonNull String str) {
        words.add(str);
    }


    /** Returns count of words without any non-words strings. */
    public int size() {
        long filteredSize = words.stream()
                .filter(Sentence::isWord)
                .count();
        return (int) filteredSize;
    }

    /**
     * Insets new word on index position.
     * The string on index position will be rewrite.
     * @param index Place for inserting
     * @param word String for inserting
     * */
    public void set(int index, String word) {
        words.set(index, word);
    }

    /**
     *  Return string from index position.
     * @param index Place of string
     * */
    public String get(int index) {
        return words.get(index);
    }

    /**
     * Replaces substring specified by index from start to end char.
     * @param start The beginning index, inclusive.
     * @param end The ending index, exclusive.
     * @param str String that will replace previous contents.
     * */
    public void replace(int index, int start, int end, String str) {
        StringBuilder sb = new StringBuilder(words.get(index));
        sb.replace(start, end, str);
        set(index, sb.toString());
    }

    /**
     * Checks a string against a word format.
     * @param str The string to be matched.
     * @return True if string is word.
     */
    private static boolean isWord(String str) {
        Matcher matcher = Pattern.compile("[A-Za-zА-Яа-я]+").matcher(str);
        return matcher.find();
    }

    /** Returns string looks like simple sentence. */
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