package lec03.task03;

import java.util.Random;

public class test {

    public static void main(String[] args) {
        int arrSize = 10;
        Number[] nums = new Number[arrSize];
        Number[] nums2 = new Number[arrSize];

        Random rnd = new Random(2000);

        Integer integer = 0;

        int r;
        for (int i =0 ; i < arrSize; i++) {
            r = rnd.nextInt();
            nums[i] = r;
            nums2[i] = r;
            if (i == 1) { integer = r; }
        }
        System.out.println(integer);
        nums = new Number[]{1,1,1,1,2};
        nums2 = new Number[]{1,2};



        MathBox<Float> mathBox = new MathBox<>(nums);
        MathBox<Float> mathBox2 = new MathBox<>(nums2);

        System.out.println("To string: " + mathBox);
        System.out.println("Summa = " + mathBox.summator());

        double divider = 3;

        mathBox.deleteEqual(integer);

        mathBox.splitter(divider);
        mathBox2.splitter(divider);

        System.out.println("To string: " + mathBox);
        System.out.println("To string: " + mathBox2);

        System.out.println("Equals: " + mathBox.equals(mathBox2));

        System.out.println("HashCode: " + mathBox.hashCode());
        System.out.println("HashCode: " + mathBox2.hashCode());

        mathBox.dump();

        mathBox.addObject(90);
        mathBox.dump();

    }



}
