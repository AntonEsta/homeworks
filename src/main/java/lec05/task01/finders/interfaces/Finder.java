package lec05.task01.finders.interfaces;

import java.util.Set;

public interface Finder<T> {

    /* TODO: доработать*/

    T find();
    T[] findAll();
    Set<T> findAllUnique();
}
