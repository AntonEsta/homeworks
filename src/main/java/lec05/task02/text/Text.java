package lec05.task02.text;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import java.util.LinkedList;
import java.util.Objects;

@EqualsAndHashCode
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Text {

    /** Collection of paragraphs. */
    final LinkedList<Paragraph> paragraphs = new LinkedList<>();

    /** Appends new paragraph to end the collection. */
    public void append(Paragraph paragraph) {
        Objects.requireNonNull(paragraph);
        paragraphs.add(paragraph);
    }

    /** Returns string looks like simple text .*/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        paragraphs.forEach(sb::append);
        return sb.toString();
    }
}
