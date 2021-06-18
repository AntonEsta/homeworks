package lec05.task02.generators;

import lec05.task02.generators.interfaces.Generator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Random;

/**
 *  Class - generator of word
 * */
@RequiredArgsConstructor
@Getter
@FieldDefaults(level= AccessLevel.PRIVATE)
public class WordGenerator implements Generator<String> {

    final int maxLengthOfWord;
    final Random rnd = new Random();

    public WordGenerator() {
        this.maxLengthOfWord = 15;
    }

    /**
     * Get a random letter
     * @return character {@code char}
     */
    private char getSomeLetter() {
        char[]latinLowerCaseCharArray= {'a','b','c','d','e','f','g','h','i','j','k','l','m',
                                        'n','o','p','q','r','s','t','u','v','w','x','y','z'};
        int i = rnd.nextInt(latinLowerCaseCharArray.length - 1) + 1;
        return latinLowerCaseCharArray[i];
    }

    /**
     * Generating a word
     * @return sequence type {@link String}
     */
    @Override
    public String generate() {
        int lengthOfWord = rnd.nextInt(maxLengthOfWord - 1) + 1;
        StringBuilder word = new StringBuilder();
        while (word.length() < lengthOfWord) {
            word.append(getSomeLetter());
        }
        return word.toString();
    }
}
