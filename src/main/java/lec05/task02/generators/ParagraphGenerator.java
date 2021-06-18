package lec05.task02.generators;

import lec05.task02.generators.interfaces.Generator;
import lec05.task02.text.Paragraph;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import java.util.Random;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)
public class ParagraphGenerator implements Generator<Paragraph> {

    final int maxLengthOfParagraph;
    final Random rnd = new Random();

    public ParagraphGenerator() {
        this.maxLengthOfParagraph = 20;
    }

    /**
     * Generates a paragraph of text
     * @return Generated paragraph of text type {@link Paragraph}
     */
    @Override
    public Paragraph generate() {
        Paragraph paragraph = new Paragraph();
        SentenceGenerator sg = new SentenceGenerator();
        int countOfSentences = rnd.nextInt(maxLengthOfParagraph -1 ) + 1;
        while (paragraph.size() < countOfSentences) {
            paragraph.append(sg.generate());
        }
        return paragraph;
    }
}