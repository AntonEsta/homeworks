package lec05.task01.finders.interfaces;

import java.util.Set;

public interface Finder<T> {

    /* TODO: доработать*/
    // Finds and return string
    T find(T text);

    // Finds and returns all
    T[] findAll(T text);

    // Returns set
    Set<T> findAllUnique(T text);
}
