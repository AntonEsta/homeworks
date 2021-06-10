package lec05.task01.sorters.interfaces;

import lec05.task01.data.StatFinder;
import lombok.NonNull;

import java.io.IOException;

public interface FileSorter extends Sorter<String> {

    void sort(@NonNull String inputFileName, @NonNull String outputFileName);

}
