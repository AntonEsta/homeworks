package petscardfile.classes.storages.abs;

import petscardfile.classes.storages.ifce.IndexedTypedStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

abstract public class AbstractIndexedTypedStorage<I, T> implements IndexedTypedStorage<I,T> {

    protected final Map<I, T> map = new ConcurrentHashMap<>();

    abstract protected I idGenerator();

    @Override
    public I add(T object) {
        I id = idGenerator();
        if (!map.containsKey(id)) {
            map.put(id, object);
            return id;
        }
        return null;
    }

    @Override
    public void remove(I index) {
        map.remove(index);
    }

    @Override
    public I getIndex(T object) {
        for (Map.Entry<I, T> entry : map.entrySet()) {
            if (entry.getValue().equals(object)) return entry.getKey();
        }
        return null;
    }

    @Override
    public Map<I,T> toMap() {
        return new HashMap<>(map);
    }

    public T get(I id) {
        return map.get(id);
    }

}
