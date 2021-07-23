package lec06.task01.factorial;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;

import java.math.BigInteger;
import java.util.concurrent.Callable;

@Getter
@EqualsAndHashCode
@ToString
@FieldDefaults(level= AccessLevel.PRIVATE)
@PackagePrivate
final class Factorial implements Callable<BigInteger> {

    int n;

    /**
     * Class constructor
     * @param n Factorial
     */
    public Factorial(int n) {
        // Factorial can't be zero less.
        if (n < 0) return;
        this.n = n;
    }

    /**
     * Calculates factorial.
     * @return The calculate result {@link BigInteger}
     */
    private BigInteger getFactorial() {
        // The value of 0! is 1, according to the convention. 1!=1 too.
        if (n == 0 || n == 1) return BigInteger.ONE;
        BigInteger result = BigInteger.ONE;
        for (int i=2; i < n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    /**  Calculates factorial */
    @Override
    public BigInteger call() {
        System.out.printf("Start factorial thread with value = %s...%n", n);
        BigInteger result = getFactorial();
        System.out.printf("Thread with value = %s was end work!%n", n);
        return result;
    }
}
