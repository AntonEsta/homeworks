package lec05.task01.sorters.classes;

import lec05.task01.data.StatFinder;
import lec05.task01.finders.classes.Finders;
import lec05.task01.sorters.interfaces.FileSorter;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@EqualsAndHashCode
@ToString
public class NioFileSorter extends FileWriter implements FileSorter {

    /*@Override
    void writeFile(Writer buffered, String[] strings) throws IOException {
        BufferedWriter bw = Files.newBufferedWriter(Paths.get(outputFileName));
        super.
        *//*try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(outputFileName))) {
            try {
                for (String s : strings) {
                    bw.write(s);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*//*
    }*/

    public void sort(@NonNull String inputFileName, @NonNull String outputFileName) {
        HashSet<String> words;
        words = new HashSet<>(Finders.newFileWordsFinder().findAllUnique(inputFileName));
        int foundWords = words.size();
//        words = new HashSet<>(words);
        writeFile(Files.newBufferedWriter(Paths.get(outputFileName)), words);
    }
}
