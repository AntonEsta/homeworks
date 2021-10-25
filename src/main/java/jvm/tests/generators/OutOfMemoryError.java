package jvm.tests.generators;

import java.util.Arrays;
import java.util.Random;

public class OutOfMemoryError implements Generator {

    @Override
    public void generate() {

        final Random rnd = new Random();
        final long rndBound = (long) (Runtime.getRuntime().maxMemory() * 0.3);

        while (true) {
            int size = rnd.nextInt((int) rndBound);
            Arrays.fill(new int[size],1);
        }

    }

}
