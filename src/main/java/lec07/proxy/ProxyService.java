package lec07.proxy;

import lec07.util.Annotations;
import lec07.test.TestInvocationHandler;
import lec07.annotations.Test;
import lec07.annotations.TimeMeasure;

import java.lang.reflect.Proxy;

/**
 * Create proxy object
 */
public class ProxyService {

    @SuppressWarnings("unchecked")
    public static <T> T proxy(T type) {

        T obj = null;

        // Check by annotation @Test or @TimeMeasure (need for testing)
        if (Annotations.isContainsAnyAnnotation(type, Test.class, TimeMeasure.class)) {
            obj = (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), type.getClass().getInterfaces(), new TestInvocationHandler(type));
        }

        if (obj != null) return obj;

        return type;

    }

}
