package lec07.sorters;

import java.util.Collection;

public interface CollectionSorter {
    <T> void sortByBubble(Collection<T> collection);
    <T> void sortByDefault(Collection<T> collection);
}
