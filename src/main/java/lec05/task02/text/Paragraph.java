package lec05.task02.text;

import lec05.task02.generators.ParagraphGenerator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import java.util.LinkedList;
import java.util.Objects;

@EqualsAndHashCode
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Paragraph{

    final LinkedList<Sentence> sentences = new LinkedList<>();

    public Paragraph append(Sentence sentence) {
        Objects.requireNonNull(sentence);
        sentences.add(sentence);
        return this;
    }

    public int size() {
        return sentences.size();
    }

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
