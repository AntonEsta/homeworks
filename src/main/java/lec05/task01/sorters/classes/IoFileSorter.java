package lec05.task01.sorters.classes;

import lec05.task01.finders.classes.FileWordFinder;
import lec05.task01.sorters.interfaces.FileSorter;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public final class IoFileSorter implements FileSorter {


    /**
     * Writes data to file. Default charset - UTF-8.
     * Exists output file will overwriting.
     * @param outputFileName file name for writes data.
     * @param strings array of {@link Iterable} data.
     * @throws NullPointerException if no data for writing.
     * @throws IOException if an I/O error occurs writing to or creating the file.
     */
    private void writeFile(@NonNull String outputFileName, @NonNull Iterable<? extends CharSequence> strings)
            throws NullPointerException, IOException {
        try (FileWriter writer = new FileWriter(outputFileName);
            Writer bw = new BufferedWriter(writer)) {
            strings.forEach((s) -> {
                        try {
                            bw.append(String.valueOf(s).toLowerCase(Locale.ROOT));
                            bw.append('\n');
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
    }

    /** Sorts words from input file and write into output file.
     *
     * @param inputFileName String path to input file.
     * @param outputFileName String path to output file.
     * */
    @Override
    public void sort(@NonNull String inputFileName, @NonNull String outputFileName){
        FileWordFinder fwf = new FileWordFinder(inputFileName);
        Set<String> set = fwf.findAll().stream()
                            .sorted(Comparator.naturalOrder())
                            .collect(Collectors.toCollection(LinkedHashSet::new));
        try {
            writeFile(outputFileName, set);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
