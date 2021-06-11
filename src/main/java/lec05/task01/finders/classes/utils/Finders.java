package lec05.task01.finders.classes.utils;

import lec05.task01.finders.classes.FileWordsFinder;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class Finders {

    public FileWordsFinder newFileWordsFinder() {
        return new FileWordsFinder();
    }



}
