package lec07.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@FieldDefaults(level=AccessLevel.PRIVATE)
public class CollectionExtender {

    /**
     * Returns a collection filled with numbers.
     * @param size number of elements
     * @return {@link Collection} of {@link Integer}
     */
    public synchronized static Collection<Integer> newNumberCollection(int size) {
        if (size < 1 ) throw new RuntimeException();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }

}
