import lombok.SneakyThrows;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class SomeClassLoader extends ClassLoader {

    private final static String someClassFileName;
    private final static String classFileDirectory;

    static {
        someClassFileName = "SomeClass";
        classFileDirectory = "/home/esta/IdeaProjects/homeworks/src/main/java";
    }

    static final class Compiler {

        /**
         * Compiles the source file "SomeClass.java" into a class file
         * @param directory Directory containing the source file of the class.
         */
        public static void compile(String directory) {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            Path path = Paths.get(directory, someClassFileName.concat(".java"));
            compiler.run(null, null, null, path.toString());
        }

    }

    /**
     * Finds class
     * @param name Loadable class name.
     * @return Found class.
     */
    @SneakyThrows
    @Override
    protected Class<?> findClass(String name) {
        Path path = Paths.get(classFileDirectory, name.concat(".class"));
        byte[] bytes;
        try ( InputStream reader = Files.newInputStream(path, StandardOpenOption.READ) ) {
            bytes = new byte[reader.available()];
            reader.read(bytes);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    /**
     * Creates a new class file "SomeClass.java".
     */
    private void createSomeClassJavaFile() {
        SomeClassSourceFileCreator sourceFileCreator = new SomeClassSourceFileCreator();
        sourceFileCreator.create();
    }

    /**
     * Compiles the class.
     */
    private void compileSomeClass() {
        Compiler.compile(classFileDirectory);
    }

    /**
     * Loads a class.
     * @param name Loadable class name.
     * @return Found class.
     * @throws ClassNotFoundException Class not found.
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // Find loaded class.
        Class<?> clazz = findLoadedClass(name);
        if ( !Objects.isNull(clazz) ) return clazz;
        if ( name.equals(someClassFileName) ) {
            createSomeClassJavaFile();
            compileSomeClass();
            return findClass(name);
        }
        return super.loadClass(name);
    }
}
