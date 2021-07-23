package lec07.test;
import lec07.util.stopwatch.StopwatchResult;
import lec07.util.timekeeper.Timekeeper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;

@FieldDefaults(level= AccessLevel.PRIVATE)
public class TestUnit {

    final Object obj;
    @Getter
    final Method method;
    @Getter
    final Object[] args;
    @Getter
    Collection<StopwatchResult> timeMeasureResults = new ArrayList<>();

    public TestUnit(Object obj, Method method, Object... args) {
        this.obj = obj;
        this.method = method;
        this.args = args;
    }

    public Duration getMinResult() {
        if (timeMeasureResults.size() == 0) return null;
        long avgNanos = timeMeasureResults.stream().mapToLong((r)-> r.getDifferenceTime().toNanos()).min().orElse(0L);
        return Duration.of(avgNanos, ChronoUnit.NANOS);
    }

    public Duration getMaxResult() {
        if (timeMeasureResults.size() == 0) return null;
        long avgNanos = timeMeasureResults.stream().mapToLong((r)-> r.getDifferenceTime().toNanos()).max().orElse(0L);
        return Duration.of(avgNanos, ChronoUnit.NANOS);
    }

    public Duration getAverageResult() {
        if (timeMeasureResults.size() == 0) return null;
        double avgNanos = timeMeasureResults.stream().mapToLong((r)-> r.getDifferenceTime().toNanos()).average().orElse(0.0);
        return Duration.of((long) avgNanos, ChronoUnit.NANOS);
    }

    public synchronized void testByTimekeeper(int testCount) throws Exception {
        this.timeMeasureResults = new ArrayList<>();


        /*
        // Variant with Threads. It's work! But I think it not right for test task.
        ExecutorService executorService = Executors.newCachedThreadPool();
        Collection<Callable<StopwatchResult>> tasks;
        tasks = new ArrayList<>();
        for (int i = 0; i < testCount; i++) {
            tasks.add(getCallableWithStopwatchResultReturn(obj, method, args));
        }
        List<Future<StopwatchResult>> futures =  executorService.invokeAll(tasks);
        executorService.shutdown();
        // Filling 'stopwatchResultCollection' from 'futures'
        futures.forEach((f)-> {
            try {
                this.timeMeasureResults.add(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        */

        // Variant with cycle.
        Timekeeper timekeeper = new Timekeeper();

        for (int i = 0; i < testCount; i++) {
            timekeeper.run(() -> {
                try {
                    return method.invoke(obj, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            });
            this.timeMeasureResults.add(timekeeper.getResult());
        }

    }

    private Callable<StopwatchResult> getCallableWithStopwatchResultReturn(Object obj, Method method, Object[] args) {
        return () -> {
            Timekeeper timekeeper = new Timekeeper();
            timekeeper.run(() -> {
                try {
                    return method.invoke(obj, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            });
            return timekeeper.getResult();
        };
    }

}
