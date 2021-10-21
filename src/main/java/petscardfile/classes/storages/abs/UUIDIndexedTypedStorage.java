package petscardfile.classes.storages.abs;

import java.util.UUID;

public class UUIDIndexedTypedStorage<T> extends AbstractIndexedTypedStorage<UUID, T> {

    @Override
    protected UUID idGenerator() {
        return UUID.randomUUID();
    }

    @Override
    public T get(UUID id) {
        return this.get(id);
    }

}
