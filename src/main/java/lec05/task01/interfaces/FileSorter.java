package lec05.task01.interfaces;

import lec05.task01.data.StatFinder;
import lombok.NonNull;

import java.io.IOException;

public interface FileSorter {

    StatFinder sort(@NonNull String inputFileName, @NonNull String outputFileName) throws IOException;

}
