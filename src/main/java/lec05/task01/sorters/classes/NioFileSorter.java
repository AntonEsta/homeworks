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
import java.util.stream.Stream;

@ToString
public final class NioFileSorter implements FileSorter {

    /**
     * Writes data to file. Default charset - UTF-8.
     * Exists output file will overwriting.
     * @param outputFileName file name for writes data.
     * @param strings array of {@link String} data.
     * @throws NullPointerException if no data for writing.
     * @throws IOException if an I/O error occurs writing to or creating the file.
     */
    private static void writeFile(@NonNull String outputFileName, @NonNull Set<String> strings) throws NullPointerException, IOException {
        Objects.requireNonNull(strings);
        String str = String.join("\n", strings).toLowerCase(Locale.ROOT);
        Files.write(Paths.get(outputFileName), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
    }

    @Override
    public void sort(@NonNull String inputFileName, @NonNull String outputFileName) {
        HashSet<String> words;
        FileWordFinder fwf = new FileWordFinder(inputFileName);
        words = fwf.findAll()
                   .sorted(Comparator.naturalOrder())
                   .collect(Collectors.toCollection(LinkedHashSet::new));
        try {
            writeFile(outputFileName, words);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*TODO: delete*/
    public static void main(String[] args) {
        final String inputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/input-skazka-o-care-saltane.txt";
        final String outputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/output-skazka-o-care-saltane.txt";
        NioFileSorter sorter = new NioFileSorter();
        sorter.sort(inputSkazka, outputSkazka);
    }
}
