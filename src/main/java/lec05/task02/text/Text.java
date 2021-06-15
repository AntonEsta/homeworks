package lec05.task02.text;

import lec05.task02.ParagraphGenerator;
import lec05.task02.SentenceGenerator;
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

    public static void main(String[] args) {
        Text text = new Text();
        ParagraphGenerator pg = new ParagraphGenerator();
//        Paragraph p = new Paragraph();
        text.append(pg.generate());
        text.append(pg.generate());
        text.append(pg.generate());
//        text.append(p);
        System.out.println(text);
    }

}
