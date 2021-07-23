package lec07.util.stopwatch;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Class for measure time.
 */
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Stopwatch {

    @Getter
    LocalDateTime startTime = LocalDateTime.MIN;
    @Getter
    LocalDateTime stopTime = LocalDateTime.MIN;
    boolean done;
    @Getter
    boolean work;
    @Getter
    final TimeUnit timeUnit = TimeUnit.NANOSECONDS;

    /**
     * Registers the reference point
     */
    public void start() {
        if (work) return;
        reset();
        done = false;
        work = true;
    }

    /**
     * Registers the breakpoint
     */
    public void stop() {
        if (done) return;
        done = true;
        work = false;
        stopTime = LocalDateTime.now();
    }

    /**
     * Resets start time to current time
     */
    public void reset() {
        startTime = LocalDateTime.now();
    }

    /**
     * Returns results of measure
     * @return {@link StopwatchResult}
     */
    public StopwatchResult getResult() {
        return new StopwatchResult(this);
    }

}
