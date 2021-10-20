package petscardfile.classes.storages.impl;

import petscardfile.classes.Person;
import petscardfile.classes.storages.abs.AbstractIndexedTypedStorage;

import java.util.UUID;

public class PersonStorage extends AbstractIndexedTypedStorage<UUID, Person> {

    @Override
    protected UUID idGenerator() {
        return UUID.randomUUID();
    }

}
