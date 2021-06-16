package lec05.task01.sorters.classes;

import lec05.task01.finders.classes.FileWordFinder;
import lec05.task01.sorters.interfaces.FileSorter;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public final class IoFileSorter implements FileSorter {

    private void writeFile(@NonNull String outputFileName, @NonNull Set<String> strings) throws NullPointerException, IOException {
        try (FileWriter writer = new FileWriter(outputFileName);
             Writer bw = new BufferedWriter(writer)) {
            strings.forEach((s) -> {
                        try {
                            bw.append(s.toLowerCase(Locale.ROOT));
                            bw.append('\n');
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
    }

    @Override
    public void sort(@NonNull String inputFileName, @NonNull String outputFileName){
        FileWordFinder fwf = new FileWordFinder(inputFileName);
        LinkedHashSet<String> stream = fwf.findAll()
                                          .sorted(Comparator.naturalOrder())
                                          .collect(Collectors.toCollection(LinkedHashSet::new));
        try {
            writeFile(outputFileName, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
