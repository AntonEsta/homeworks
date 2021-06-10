package lec05.task01.sorters.classes;

import lec05.task01.data.StatFinder;
import lec05.task01.finders.classes.Finders;
import lec05.task01.sorters.interfaces.FileSorter;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.*;
import java.util.*;

@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IoFileSorter implements FileSorter {

//    FileWriter fw;

    /*void writeFile(String outputFileName, String[] strings) throws IOException {
        try (FileWriter writer = new FileWriter(outputFileName);
             BufferedWriter bw = new BufferedWriter(writer)) {
            stream.forEach((s) -> {
                try {
                    bw.write(s);
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
*/
    @Override
    public StatFinder sort(@NonNull String inputFileName, @NonNull String outputFileName) throws IOException {
        HashSet<String> words;
        words = new HashSet<>(Finders.newFileWordsFinder().findAllUnique(inputFileName));
        /* TODO: int foundWords - фиксировать с повторениями или удалить функционал  */
        int foundWords = words.size();
//        words = new HashSet<>(words);
        FileWriter writer = new FileWriter(outputFileName);
        BufferedWriter bw = new BufferedWriter(writer);
        fw.writeFile(bw, words);
        return new StatFinder(foundWords, words.size());
    }
}
