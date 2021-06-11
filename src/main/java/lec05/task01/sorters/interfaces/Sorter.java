package lec05.task01.sorters.interfaces;

import lombok.NonNull;

import java.io.IOException;

public interface Sorter<T> {

    void sort(@NonNull T in, @NonNull T out) throws IOException;

}
