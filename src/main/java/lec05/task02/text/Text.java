package lec05.task02.text;

import lec05.task02.generators.ParagraphGenerator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import java.util.LinkedList;
import java.util.Objects;

@EqualsAndHashCode
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Text {

    final LinkedList<Paragraph> paragraphs = new LinkedList<>();

    public Text append(Paragraph paragraph) {
        Objects.requireNonNull(paragraph);
        paragraphs.add(paragraph);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        paragraphs.forEach(sb::append);
        return sb.toString();
    }
}
