package lec05.task01;

import lec05.task01.data.CharacterTables;
import lec05.task01.data.StatFinder;
import lec05.task01.interfaces.FileSorter;
import lombok.NonNull;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Stream;

//@UtilityClass
public class NioSort implements FileSorter {

    private boolean isLetter(char ch) {
        return Arrays.binarySearch(CharacterTables.latinLettersCharArray, ch) >= 0 ||
                Arrays.binarySearch(CharacterTables.cyrillicLettersCharArray, ch) >= 0;
    }

    public StatFinder sort(@NonNull String inputFileName, @NonNull String outputFileName) throws IOException {

        int foundWords = 0;
        HashSet<String> words = new HashSet<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFileName))) {
            StringBuilder str = new StringBuilder();
            int ch;
            while ((ch = br.read()) != -1) {
                if (isLetter((char)ch)) {
                    str.append((char) ch);
                } else {
                    if (str.toString().isEmpty()) continue;
                    foundWords++;
                    words.add(str.toString().toLowerCase());
                    str = new StringBuilder();
                }
            }
        }

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(outputFileName))) {
            Stream<String> stream = words.stream().sorted(Comparator.naturalOrder());
            stream.forEach((s) -> {
                try {
                    bw.write(s);
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return new StatFinder(foundWords, words.size());
    }

}
