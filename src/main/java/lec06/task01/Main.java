package lec06.task01;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        LinkedHashSet<BigInteger> values = new LinkedHashSet<>();

        Random random = new Random();
        int randomBound = 100;

        for (int i = 0; i < 100; i++) {
            values.add(BigInteger.valueOf(random.nextInt(randomBound)));
        }

        LinkedList<Callable<BigInteger>> callables = new LinkedList<>();

        for (BigInteger val : values) {
            callables.addFirst(new Factorial<>(val.intValue()));
        }

        ExecutorService pool = Executors.newFixedThreadPool(values.size());

        ArrayList<Future<BigInteger>> futures = new ArrayList<>();

        try {
            futures = (ArrayList<Future<BigInteger>>) pool.invokeAll(callables);
        } catch (Exception e) {
            e.getStackTrace();
        }

        pool.shutdown();

        for (BigInteger bigint : values) {
            System.out.println(bigint);
        }
        System.out.println();
        for (Future<BigInteger> f : futures) {
            try {
                System.out.println(f.get());
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        System.out.println(Factorials.getFact(values));
    }
}
