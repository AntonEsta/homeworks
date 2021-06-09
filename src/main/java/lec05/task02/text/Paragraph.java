package lec05.task02.text;

import lombok.Data;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

@Data
public class Paragraph{

    private LinkedList<Sentence> sentences = new LinkedList<>();

    public Paragraph append(Sentence sent) {
        Objects.requireNonNull(sent);
        sentences.add(sent);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Sentence str : sentences) {
            sb.append(str);
        }
        return sentences.toString();
    }


}
