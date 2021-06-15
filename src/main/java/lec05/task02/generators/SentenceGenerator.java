package lec05.task02.generators;

import lec05.task02.generators.interfaces.Generator;
import lec05.task02.text.Sentence;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Random;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@FieldDefaults(level= AccessLevel.PRIVATE)
public class SentenceGenerator implements Generator<Sentence> {

    final String separator;
    final int maxCountOfWords;
    final char[] endOfSentence;

    final Random rnd = new Random();

    public SentenceGenerator() {
        this.separator = " ";
        this.maxCountOfWords = 15;
        this.endOfSentence = new char[]{'.', '!', '?'};
    }

    private boolean needComma() {
        return (rnd.nextInt(30) == 20);
    }

    private String getSomeEnding() {
        int ch = rnd.nextInt(endOfSentence.length - 1);
        ch = endOfSentence[ch];
        return String.valueOf((char) ch);
    }

    /**
     * Генерация предложения
     * @return сгенерированное предложение типа {@link Sentence}
     */
    @Override
    public Sentence generate() {
        Sentence sentence = new Sentence();
        int countOfWords = rnd.nextInt(maxCountOfWords - 1) + 1;
        while (sentence.size() < countOfWords) {
            sentence.append(new WordGenerator().generate());
            if (needComma()) { sentence.append(","); }
        }
        sentence.append(getSomeEnding());
        sentence.replace(0, 0, 0, String.valueOf(sentence.get(0).charAt(0)).toUpperCase());
        return sentence;
    }

    /*TODO: The method below is to be removed.*/
    public static void main(String[] args) {
        SentenceGenerator sg = new SentenceGenerator();
        Sentence s = sg.generate();
        System.out.println(s);
    }
}
