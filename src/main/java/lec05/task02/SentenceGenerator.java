package lec05.task02;

import lec05.task02.interfaces.Generator;
import lec05.task02.text.Sentence;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Random;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@FieldDefaults(level= AccessLevel.PRIVATE)
public class SentenceGenerator implements Generator<Sentence> {

    private final String separator;
    private final int maxCountOfWords;
    private final char[] endOfSentence;

    Random rnd = new Random();

    public SentenceGenerator() {
        this.separator = " ";
        this.maxCountOfWords = 15;
        this.endOfSentence = new char[]{'.', '!', '?'};
    }

    private String getSeparator() {
        return (rnd.nextInt(30) != 20) ? this.separator : ", ";
    }

    private String getSomeEnding() {
        int ch = rnd.nextInt(endOfSentence.length - 1);
        ch = endOfSentence[ch];
        return String.valueOf(ch);
    }

    /*
     *  Генерация предложения
     * */
    @Override
    public Sentence generate() {
        Sentence sentence = new Sentence();
        StringBuilder sb = new StringBuilder();
        int countOfWords = rnd.nextInt(maxCountOfWords - 1) + 1;
        int counterWords = 0;
        while (counterWords < countOfWords) {
            sb.append(new WordGenerator().generate());
            sb.append(getSeparator());
            counterWords++;
        }
        sb.replace(sb.length(), sb.length(), getSomeEnding());
        sb.replace(0, 0, String.valueOf(sb.charAt(0)).toUpperCase());
        sentence.append(sb.toString());
        return sentence;
    }
}
