package lec06.task01;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
@EqualsAndHashCode
@ToString
@FieldDefaults(level= AccessLevel.PUBLIC)
public class Factorial implements Callable<BigInteger> {

    int n;

    public Factorial(@NonNull int n) {
        if (n < 1) return;
        this.n = n;
    }

    /**  */
    public void setN(@NonNull int n) throws NullPointerException{
        if (n < 1) throw new NumberFormatException("Expected \"n > 0\"");
        this.n = n;
    }

    public static Collection<BigInteger> getFactorials(Collection<BigInteger> values) {

        ConcurrentLinkedQueue<BigInteger> queue = (ConcurrentLinkedQueue<BigInteger>) values;

        queue.stream().forEach();

    }

    /**  Вычисляет факториал */
    @Override
    public BigInteger call() {
        BigInteger result = BigInteger.valueOf(1);
        for (int i=2; i < n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}
