package lec07.util.timekeeper;

import lec07.ObjectManager;
import lec07.util.stopwatch.StopwatchResult;
import lec07.util.stopwatch.Stopwatch;
import lombok.Getter;

import java.util.function.Supplier;

/**
 * Class can used for time measure method execution
 */
public class Timekeeper {

    @Getter
    private final Stopwatch stopwatch = ObjectManager.getObject(Stopwatch.class);

    /**
     * Performs the actions specified in {@link Supplier} measuring the start and end times of a task.
     * @param action Represents a supplier of results.
     */
    @SuppressWarnings("unused")
    public void run(Runnable action) {
        stopwatch.reset();
        stopwatch.start();
        action.run();
        stopwatch.stop();
    }

    /**
     * Performs the actions specified in {@link Supplier} measuring the start and end times of a task.
     * @param action Represents a supplier of results.
     * @param <T> extends {@link Object}
     * @return result of action
     */
    public <T> T run(Supplier<T> action) {
        stopwatch.reset();
        stopwatch.start();
        T result = action.get();
        stopwatch.stop();
        return result;
    }

    /**
     * Gets results of time measure.
     * @return Results {@link StopwatchResult}
     */
    public StopwatchResult getResult() {
        return new StopwatchResult(stopwatch);
    }

}
