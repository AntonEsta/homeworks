package jvm.tests.generators;

import javassist.ClassPool;
import javassist.CtClass;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collection;

public class MetaspaceError implements Generator {

    @Override
    @SneakyThrows
    public void generate() {
        String name = "jvm.tests.generators.EmptyClass";
        Collection<Class<?>> c = new ArrayList<>();
        for (int i = 0; ; i++) {
            CtClass cl = ClassPool.getDefault().getAndRename(name, name + i);
            final Class<?> aClass = cl.toClass();
            c.add(aClass);
            System.out.print("\rObjects count: " + c.size());
        }
    }

}
