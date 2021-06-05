package lec06.task01;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigInteger;
import java.util.concurrent.Callable;

@Getter
@EqualsAndHashCode
@ToString
public class Factorial<T extends BigInteger> implements Callable<T> {

    private int n;

    public Factorial(@NonNull int n) {
        if (n < 1) return;
        this.n = n;
    }

    public void setN(@NonNull int n) {
        if (n < 1) throw new NumberFormatException("Expected \"n > 0\"");
        this.n = n;
    }

    @Override
    public T call() {

        BigInteger result = BigInteger.valueOf(1);

        for (int i=2; i < n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }

        return (T) result;
    }
}
