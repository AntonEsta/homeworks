package lec06.task01;

import lec06.task01.factorial.Factorials;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {

        // Calculate single factorial.
        try {
            System.out.println("10! = " + Factorials.factorial(10));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Collection<Integer> values = new LinkedHashSet<>();
        Random random = new Random();
        int randomBound = 100;
        final int valCount = 10;

        // Set the values.
        while (values.size() < valCount) {
            values.add(random.nextInt(randomBound));
        }

        // Print the original values.
        System.out.printf("The start values %s %n", values);

        List<BigInteger> results = new ArrayList<>();

        // Start getFactorials method.
        System.out.println();
        System.out.println("-- Start getFactorials() method...");
        try {
            results = Factorials.getFactorials(values);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("-- End getFactorial() method work!");

        // Print the end values
        System.out.println("The end values.");
        results.forEach(System.out::println);
    }

}
