package lec07.util.stopwatch;

import lombok.NonNull;
import lombok.Value;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Class for displaying the results of the class {@link Stopwatch}
 */
@Value
public class StopwatchResult {
    LocalDateTime startTime;
    LocalDateTime stopTime;

    public StopwatchResult(@NonNull Stopwatch stopwatch) {
        startTime = stopwatch.getStartTime();
        stopTime = stopwatch.getStopTime();
    }

    @SuppressWarnings("unused")
    public StopwatchResult(@NonNull LocalDateTime startTime, @NonNull LocalDateTime stopTime) {
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    /**
     * Calculate difference time between start and end time.
     * @return {@link Duration}
     */
    public Duration getDifferenceTime() {
        return Duration.between(startTime, stopTime);
    }

    public String toString() {
        return Duration.between(startTime, stopTime).toString();
    }

}
