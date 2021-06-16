package lec06.task01;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@UtilityClass
public class Factorials {

    public static List<BigInteger> getFactorials(@NonNull Collection<BigInteger> values) throws InterruptedException {

        ExecutorService threads = Executors.newFixedThreadPool(values.size());
        List<Callable<BigInteger>> callables = new ArrayList<>();


//        List<Future<BigInteger>> bigints = new ArrayList<>();

       /* for (BigInteger value : values) {
            callables.add(new Factorial(value.intValue()));
        }*/

        /* TODO: continue here */
        List<BigInteger> result = threads.invokeAll(callables).stream().collect(Collectors::toList());






        Collection<Callable<BigInteger>> callables = new LinkedList<>();


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

    public static void main(String[] args) {

        /*TODO: уточнить тип коллекции*/
        Set<BigInteger> values = new HashSet<>();

        Random random = new Random();
        int randomBound = 100;
        final int valCount = 100;

        while (values.size() < valCount) {
            values.add(BigInteger.valueOf(random.nextInt(randomBound)));
        }

        List<Callable<BigInteger>> callables = new LinkedList<>();

        for (BigInteger val : values) {
            callables.add(new Factorial(val.intValue()));
        }

        ExecutorService pool = Executors.newFixedThreadPool(callables.size());

        List<Future<BigInteger>> futures = new ArrayList<>();

        try {
            futures = pool.invokeAll(callables);
        } catch (Exception e) {
            e.getStackTrace();
        }

        pool.shutdown();

        // Вывод результатов
        values.forEach(System.out::println);

        System.out.println();

        for (Future<BigInteger> f : futures) {
            try {
                System.out.println(f.get());
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        System.out.println(Factorials.getFactorials(values));
    }

}
