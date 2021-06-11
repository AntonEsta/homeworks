package lec05.task01.sorters.classes;

import lec05.task01.finders.classes.utils.Finders;
import lec05.task01.sorters.interfaces.FileSorter;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.io.*;
import java.util.*;

@EqualsAndHashCode
@ToString
public class IoFileSorter implements FileSorter {

    private void writeFile(@NonNull String outputFileName, @NonNull String[] strings) throws NullPointerException, IOException {
        if (strings == null) {
            throw new NullPointerException("No data for writing! (String[] strings is null)");
        }

        try (FileWriter writer = new FileWriter(outputFileName);
             Writer bw = new BufferedWriter(writer)) {
            Arrays.stream(strings).forEach((s) -> {
                        try {
                            bw.append(s);
                            bw.append('\n');
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
    }

    @Override
    public void sort(@NonNull String inputFileName, @NonNull String outputFileName) throws IOException {
        HashSet<String> words;
        words = new HashSet<>(Finders.newFileWordsFinder().findAllUnique(inputFileName));
        writeFile(outputFileName, words.toArray(new String[0]));
    }
}
