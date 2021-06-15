package lec05.task02.text;

import lec05.task02.ParagraphGenerator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Class paragraph generator.
 */
@EqualsAndHashCode
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Paragraph{

    final LinkedList<Sentence> sentences = new LinkedList<>();

    public Paragraph append(Sentence sent) {
        Objects.requireNonNull(sent);
        sentences.add(sent);
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

    /*TODO: delete*/
    public static void main(String[] args) {
        ParagraphGenerator pg = new ParagraphGenerator();
        Paragraph p = pg.generate();
        System.out.println(p);
    }

}
