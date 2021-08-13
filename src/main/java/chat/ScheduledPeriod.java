package chat;

import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public class ScheduledPeriod {
    private final long period;
    private final TimeUnit timeUnit;
}
