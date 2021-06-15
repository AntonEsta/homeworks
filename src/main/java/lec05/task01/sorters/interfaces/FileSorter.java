package lec05.task01.sorters.interfaces;

import lombok.NonNull;

public interface FileSorter extends Sorter<String> {

    void sort(@NonNull String inputFileName, @NonNull String outputFileName);

}
