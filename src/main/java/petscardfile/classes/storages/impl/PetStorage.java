package petscardfile.classes.storages.impl;

import petscardfile.classes.Pet;
import petscardfile.classes.storages.abs.AbstractIndexedTypedStorage;

import java.util.UUID;

public class PetStorage extends AbstractIndexedTypedStorage<UUID, Pet> {

    @Override
    protected UUID idGenerator() {
        return UUID.randomUUID();
    }

}
