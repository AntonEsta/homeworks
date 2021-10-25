package jvm.monitors;

import lombok.NonNull;
import lombok.SneakyThrows;

public class MemoryMonitor implements Runnable {

    @SneakyThrows
    private void monitoring(){
        double prePercent = 0;
        while (true) {
            final long maxMemory = Runtime.getRuntime().maxMemory();
            final long freeMemory = Runtime.getRuntime().freeMemory();
            final double percent = percent(freeMemory, maxMemory);
            if (percent != prePercent) {
                System.out.printf("Used memory space: %.4s%%%n", percent);
                prePercent = percent;
            }
        }

    }

    private double percent(@NonNull Number x, @NonNull Number y){
        return x.doubleValue() / y.doubleValue() * 100;
    }

    @Override
    public void run() {
        this.monitoring();
    }

}
