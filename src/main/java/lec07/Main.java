package lec07;

import lec07.sorters.AnyCollectionSorter;
import lec07.sorters.CollectionSorter;
import lec07.util.CollectionExtender;

import java.time.LocalDateTime;
import java.util.*;

class Main {

    public static void main(String[] args) {

        final int[] sizeCollection = new int[]{10_000, 100_000};//, 1_000_000};
        final CollectionSorter collectionSorter = ObjectManager.getObject(AnyCollectionSorter.class);

        for (int size : sizeCollection) {
            System.out.println(size + " start at " + LocalDateTime.now());
            List<Integer> numbers = (List<Integer>) CollectionExtender.newNumberCollection(size);
            collectionSorter.sortByBubble(numbers);
            System.out.println(size + " Complete at " + LocalDateTime.now());
        }

    }

}
