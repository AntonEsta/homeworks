package lec05.task01.sorters.classes;

import lec05.task01.finders.classes.FileWordFinder;
import lec05.task01.sorters.interfaces.FileSorter;

import lombok.NonNull;
import lombok.ToString;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

@ToString
public final class NioFileSorter implements FileSorter {

    /**
     * Writes data to file. Default charset - UTF-8.
     * Exists output file will overwriting.
     * @param outputFileName file name for writes data.
     * @param strings array of {@link Iterable} data.
     * @throws NullPointerException if no data for writing.
     * @throws IOException if an I/O error occurs writing to or creating the file.
     */
    private static void writeFile(@NonNull String outputFileName, @NonNull Iterable<? extends CharSequence> strings)
            throws NullPointerException, IOException {
        Objects.requireNonNull(strings);
        String str = String.join("\n", strings).toLowerCase(Locale.ROOT);
        Files.write(Paths.get(outputFileName), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
    }

    /** Sorts words from input file and write into output file.
     *
     * @param inputFileName String path to input file.
     * @param outputFileName String path to output file.
     * */
    @Override
    public void sort(@NonNull String inputFileName, @NonNull String outputFileName) {
        HashSet<String> words;
        FileWordFinder fwf = new FileWordFinder(inputFileName);
        words = fwf.findAll().stream()
                   .sorted(Comparator.naturalOrder())
                   .collect(Collectors.toCollection(LinkedHashSet::new));
        try {
            writeFile(outputFileName, words);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
