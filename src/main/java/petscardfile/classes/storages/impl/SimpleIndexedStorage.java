package petscardfile.classes.storages.impl;

import petscardfile.classes.storages.abs.AbstractIndexedTypedStorage;

import java.util.UUID;

public class SimpleIndexedStorage<T> extends AbstractIndexedTypedStorage<UUID, T> {

    @Override
    protected UUID idGenerator() {
        return UUID.randomUUID();
    }

}
