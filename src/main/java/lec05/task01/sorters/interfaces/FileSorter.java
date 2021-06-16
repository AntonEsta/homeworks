package lec05.task01.sorters.interfaces;

import lombok.NonNull;

public interface FileSorter {

    void sort(@NonNull String inputFileName, @NonNull String outputFileName);

}
