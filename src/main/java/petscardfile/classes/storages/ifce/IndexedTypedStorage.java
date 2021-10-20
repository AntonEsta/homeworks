package petscardfile.classes.storages.ifce;

import petscardfile.classes.Person;

import java.util.Map;
import java.util.UUID;

public interface IndexedTypedStorage<I,T> {

    I add(T object);

    void remove(I o);

    I getIndex(T object);

    Map<I,T> toMap();

    T get(UUID petId);
}
