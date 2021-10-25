package jvm.tests.generators;

import java.lang.reflect.InvocationTargetException;

public interface Generator {

    void generate() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

}
