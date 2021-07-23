package lec07;

import lec07.proxy.ProxyService;


public class ObjectManager {

    public static <T> T getObject(Class<T> type, Object... args) {

        T obj = ObjectProvider.createObject(type, args);

        obj = ProxyService.proxy(obj);

        return obj;
    }

}
