package lec05.task01.classes;

import lec05.task01.data.StatFinder;
import lec05.task01.interfaces.FileSorter;
import lombok.NonNull;
import java.io.*;
import java.util.*;
import java.util.stream.Stream;

//@UtilityClass
public class IoSort implements FileSorter {

    @Override
    public StatFinder sort(@NonNull String inputFileName, @NonNull String outputFileName) throws IOException {

        int foundWords = 0;
        HashSet<String> words;

        FileWordsFinder fwf = new FileWordsFinder();
        words = (HashSet<String>) fwf.findAll(inputFileName);

        foundWords = words.size();

        try (FileWriter writer = new FileWriter(outputFileName);
             BufferedWriter bw = new BufferedWriter(writer)) {
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
