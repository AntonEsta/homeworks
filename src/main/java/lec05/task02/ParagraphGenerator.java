package lec05.task02;

import lec05.task02.interfaces.Generator;
import lec05.task02.text.Paragraph;

import java.util.Random;

public class ParagraphGenerator implements Generator<Paragraph> {

    private final int maxLengthOfParagraph = 20;

    private Random rnd = new Random();

    @Override
    public Paragraph generate() {
        Paragraph paragraph = new Paragraph();
        int counterSentences = 0;
        int countOfSentences = rnd.nextInt(maxLengthOfParagraph -1 ) + 1;
        while (counterSentences < countOfSentences) {
            paragraph.append(new SentenceGenerator().generate());
            counterSentences++;
        }
//        paragraph.replace(paragraph.length() - 1, paragraph.length(), "\n");
        return paragraph;
    }
}
