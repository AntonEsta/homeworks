package petscardfile.classes.storages;

import petscardfile.classes.Person;
import petscardfile.classes.Pet;
import petscardfile.classes.storages.abs.AbstractIndexedTypedStorage;
import petscardfile.classes.storages.ifce.IndexedTypedStorage;
import petscardfile.classes.storages.impl.PersonStorage;
import petscardfile.classes.storages.impl.PetStorage;
import petscardfile.classes.storages.impl.RelationStorage;

import java.util.UUID;

public final class StorageFactory {

    public static <T> IndexedTypedStorage<UUID, T> newUuidStorage(Class<T> cl) {
        return new AbstractIndexedTypedStorage<UUID, T>(){
            @Override
            protected UUID idGenerator() {
                return UUID.randomUUID();
            }
        };
    }

    public static IndexedTypedStorage<UUID, Pet> newPetStorage() {
        return new PetStorage();
    }

    public static IndexedTypedStorage<UUID, Person> newPersonStorage() {
        return new PersonStorage();
    }

    public static RelationStorage newRelationStorage() {
        return new RelationStorage();
    }
}
