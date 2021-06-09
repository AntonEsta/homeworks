package lec05.task02;

import lec05.task01.data.CharacterTables;
import lec05.task02.interfaces.Generator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Random;

@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class WordGenerator implements Generator<String> {

    final int maxLengthOfWord = 15;

    private Random rnd = new Random();

    private int getSomeLetter() {
        return rnd.nextInt(CharacterTables.latinLowerCaseCharArray.length - 1) + 1;
    }

    @Override
    public String generate() {
        int lengthOfWord = rnd.nextInt(maxLengthOfWord - 1) + 1;
        StringBuilder word = new StringBuilder();
        while (word.length() < lengthOfWord) {
            int i = getSomeLetter();
            word.append(CharacterTables.latinLowerCaseCharArray[i]);
        }
        return word.toString();
    }
}
