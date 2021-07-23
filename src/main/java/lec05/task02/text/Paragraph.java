package lec05.task02.text;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import java.util.LinkedList;
import java.util.Objects;

@EqualsAndHashCode
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Paragraph{

    final LinkedList<Sentence> sentences = new LinkedList<>();

    /** Appends new string to end the collection
     * @param sentence {@link Sentence} for appending.
     * */
    public void append(Sentence sentence) {
        Objects.requireNonNull(sentence);
        sentences.add(sentence);
    }

    /** Returns count of sentences. */
    public int size() {
        return sentences.size();
    }

    /** Returns string looks like simple paragraph. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Sentence str : sentences) {
            sb.append(str);
            sb.append(" ");
        }
        sb.replace(sb.length(), sb.length(), "\n");
        return sb.toString();
    }
}
