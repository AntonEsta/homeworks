package petscardfile.classes.storages.impl;

import petscardfile.classes.storages.abs.AbstractRelationsStorage;

import java.util.UUID;

public class RelationStorage extends AbstractRelationsStorage<UUID, UUID> {

    @Override
    protected UUID idGenerator() {
        return UUID.randomUUID();
    }

}
