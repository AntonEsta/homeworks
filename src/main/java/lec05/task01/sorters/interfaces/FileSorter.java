package lec05.task01.sorters.interfaces;

import lombok.NonNull;

/**
 * The class defines the behavior of the sorter class.
 */
public interface FileSorter {

    void sort(@NonNull String inputFileName, @NonNull String outputFileName);

}
