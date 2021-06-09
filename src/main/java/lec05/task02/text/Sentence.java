package lec05.task02.text;

import lombok.Data;
import java.util.LinkedList;
import java.util.Objects;


@Data
public class Sentence {

    private LinkedList<String> words = new LinkedList(){
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            forEach(sb::append);
            return sb.toString();
        }
    };

    public Sentence append(String str) {
        Objects.requireNonNull(str);
        words.add(str);
        return this;
    }

    public int length() { return words.size(); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        words.forEach(sb::append);
        return sb.toString();
    }
}
