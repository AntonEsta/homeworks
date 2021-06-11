package lec05.task01.sorters.classes;

import lec05.task01.finders.classes.utils.Finders;
import lec05.task01.sorters.interfaces.FileSorter;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

@EqualsAndHashCode
@ToString
public class NioFileSorter implements FileSorter {

    /**
     * Writes data to file. Default charset - UTF-8.
     * Exists output file will overwriting.
     * @param outputFileName file name for writes data.
     * @param strings array of {@link String} data.
     * @throws NullPointerException if no data for writing.
     * @throws IOException if an I/O error occurs writing to or creating the file.
     */
    private void writeFile(@NonNull String outputFileName, @NonNull String[] strings) throws NullPointerException, IOException {
        if (strings == null) {
            throw new NullPointerException("No data for writing! (String[] strings is null)");
        }

        String str = String.join(String.valueOf('\n'), strings);
        Files.write(Paths.get(outputFileName), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
    }

    @Override
    public void sort(@NonNull String inputFileName, @NonNull String outputFileName) throws IOException {
        HashSet<String> words;
        words = new HashSet<>(Finders.newFileWordsFinder().findAllUnique(inputFileName));
        writeFile(outputFileName, words);
    }
}
