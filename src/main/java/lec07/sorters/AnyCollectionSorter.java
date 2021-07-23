package lec07.sorters;

import lec07.annotations.DefaultPath;
import lec07.annotations.Test;
import lec07.annotations.TimeMeasure;

import java.util.*;

@SuppressWarnings("unused")
@Test(testCount = 10,
        CompareMethodsByTimeRun = {"sortByBubble","sortByDefault"})
//@DefaultPath("/home/esta/IdeaProjects/homeworks/src/main/java/lec07/test_results")  // <- it's work!
public class AnyCollectionSorter implements CollectionSorter {

    /**
     * Sorts collection by Bubble's method
     * @param collection collection to be sorted
     * @param <T> any type extends {@link Object} class
     */

    @TimeMeasure
    @SuppressWarnings("unchecked")
    @Override
    public synchronized <T> void sortByBubble(Collection<T> collection) {
        Objects.requireNonNull(collection);
        List<T> list = new ArrayList<>(collection);
        if (list.size() == 1) return;
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < list.size() - 1; j++) {
                if (((Comparable<T>) list.get(i)).compareTo(list.get(j+1)) > 0) {
                    T tmp = list.get(j);
                    list.set(j, list.get(j+1));
                    list.set(j+1, tmp);
                }
            }
        }
    }

    /**
     * Sorts collection by default method
     * @param collection collection to be sorted
     * @param <T> any type extends {@link Object} class
     */
    @TimeMeasure
    @Override
    public synchronized <T> void sortByDefault(Collection<T> collection) {
        Objects.requireNonNull(collection);
        List<T> list = new ArrayList<>(collection);
        list.sort(null);
    }

}
