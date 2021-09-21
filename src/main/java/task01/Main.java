package task01;

import jvm.monitors.MemoryMonitor;
import jvm.tests.generators.Generator;
import jvm.tests.generators.OutOfMemoryError;

public class Main {

    public static void main(String[] args) {
        final Thread thread = new Thread(new MemoryMonitor());
        thread.start();

        final Generator outOfMemoryError = new OutOfMemoryError();
        try {
            outOfMemoryError.generate();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(0);
        }
    }

}
