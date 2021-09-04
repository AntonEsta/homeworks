package chat.messanger;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * Time period.
 */
@Data
public class ScheduledPeriod {
    private final long period;
    private final TimeUnit timeUnit;
}
