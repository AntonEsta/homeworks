package lec05.task02.text;

import lombok.Data;

import java.util.LinkedList;
import java.util.Objects;

@Data
public class Text {

    LinkedList<Paragraph> paragraphs = new LinkedList<>();

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
