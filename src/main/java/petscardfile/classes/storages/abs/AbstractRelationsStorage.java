package petscardfile.classes.storages.abs;

import lombok.SneakyThrows;
import petscardfile.classes.storages.ifce.RelationsStorage;

import javax.management.OperationsException;

abstract public class AbstractRelationsStorage<I,T> extends AbstractIndexedTypedStorage<I,T> implements RelationsStorage {

    @SneakyThrows
    @Override
    public I add(T object) {
        throw new OperationsException("Operation not supported!");
    }

    @Override
    public void add(Object petId, Object ownerId) {
        map.put((I) petId, (T) ownerId);
    }
}
