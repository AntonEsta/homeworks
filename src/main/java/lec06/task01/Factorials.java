package lec06.task01;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;

@UtilityClass
public class Factorials {

    public  List<? extends BigInteger> getFact(@NonNull Collection<? extends BigInteger> values) {

        ThreadPoolExecutor threads = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        LinkedList<Callable<BigInteger>> callables = new LinkedList<>();
        ArrayList<Future<BigInteger>> bigints = new ArrayList<>();

        for (BigInteger value : values) {
            callables.add(new Factorial<>(value.intValue()));
        }

        try {
            bigints = (ArrayList<Future<BigInteger>>) threads.invokeAll(callables);
        } catch (InterruptedException e) {
            e.getStackTrace();
        } finally {
            System.out.printf("Start thread: %s%n", threads.getTaskCount());
            threads.shutdown();
        }

        List<BigInteger> result = new LinkedList<>();

        for (Future<BigInteger> future : bigints) {
            try {
                result.add(future.get());
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        return result;
    }
}
