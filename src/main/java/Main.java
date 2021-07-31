
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        SomeClassLoader classLoader = new SomeClassLoader();
        Class<?> clazz = classLoader.loadClass("SomeClass");
        Worker worker = (Worker) clazz.newInstance();
        worker.doWork();
    }

}
