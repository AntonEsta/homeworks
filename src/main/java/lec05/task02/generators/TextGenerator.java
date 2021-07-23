package lec05.task02.generators;

import lec05.task02.generators.interfaces.Generator;
import lec05.task02.text.Text;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import java.util.Random;

/**
 *  Class - generator of text
 * */
@Getter
@FieldDefaults(level=AccessLevel.PRIVATE)
public class TextGenerator implements Generator<Text> {

    final int countOfParagraph;
    final Random rnd = new Random();

    public TextGenerator() {
        this.countOfParagraph = 3;
    }

    /**
     *  Text generation
     * @return generated text like {@link Text}
     */
    @Override
    public Text generate() {
        Text text = new Text();
        int counterParagraph = 0;
        while (counterParagraph < countOfParagraph) {
            text.append(new ParagraphGenerator().generate());
            counterParagraph++;
        }
        return text;
    }
}
