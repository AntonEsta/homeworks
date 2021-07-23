package lec07;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.util.Objects;

public class ObjectProvider {

    public static <T> T createObject(Class<T> type, Object... args) {
        T obj = createObject0(type, args);
        // Perhaps sometime you will need to perform some actions before returning the object
        return obj;
    }

    @SneakyThrows
    private static <T> T createObject0(Class<T> type, Object... args) {

        if (args.length == 0) return type.newInstance();
        Class<?>[] paramTypes = new Class[args.length];

        for (int i = 0; i < args.length; i++) {
            paramTypes[i] = args[i].getClass();
        }

        Constructor<T> constructor= type.getDeclaredConstructor(paramTypes);
        Objects.requireNonNull(constructor);
        return constructor.newInstance(args);
    }

}
