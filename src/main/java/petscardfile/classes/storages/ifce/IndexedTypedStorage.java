package petscardfile.classes.storages.ifce;

import java.util.Map;

public interface IndexedTypedStorage<I,T> {

    I add(T object);

    void remove(I o);

    I getIndex(T object);

    Map<I,T> toMap();

    T get(I id);
}
