package petscardfile.classes.storages;

import petscardfile.classes.storages.impl.*;

public final class StorageFactory {

    public static PetStorage newPetStorage() {
        return new PetStorage();
    }

    public static PersonStorage newPersonStorage() {
        return new PersonStorage();
    }

    public static RelationStorage newRelationStorage() {
        return new RelationStorage();
    }

}
