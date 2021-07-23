package lec06.task01.factorial;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;

@UtilityClass
public class Factorials {


    /**
     * The method calculates the factorials of original values of the collection.
     *
     * @param values Collection of original values.
     * @return List of {@link BigInteger}
     */
    public static ArrayList<BigInteger> getFactorials(@NonNull Collection<Integer> values)
                                            throws InterruptedException, ExecutionException {

        // Create ThreadPool with size for 10 tasks
        ExecutorService threads = Executors.newFixedThreadPool(10);

        // Get tasks into callables collection
        List<Callable<BigInteger>> callables = new ArrayList<>();
        values.forEach(v -> callables.add(new Factorial(v)));

        // Doing all added tasks and shutdown service at the end.
        List<Future<BigInteger>> futures = new ArrayList<>(threads.invokeAll(callables));
        threads.shutdown();

        ArrayList<BigInteger> result = new ArrayList<>();

        // Get future's results.
        for (Future<BigInteger> i: futures) {
            result.add(i.get());
        }

        return result;
    }

    /**
     * Calculate single factorial.
     * @param n The integer
     * @return The calculate result {@link BigInteger}
     */
    public static BigInteger factorial(int n) throws ExecutionException, InterruptedException {
        RunnableFuture<BigInteger> task = new FutureTask<>(new Factorial(n));
        new Thread(task).start();
        return task.get();
    }
}
