package lec05.task01;

import java.io.IOException;

public class TestProg {
    public static void main(String[] args) {

        final String inputLorem = "/home/esta/IdeaProjects/homeworks/src/main/resources/input.txt";
        final String outputLorem = "/home/esta/IdeaProjects/homeworks/src/main/resources/output.txt";
        final String inputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/input-skazka-o-care-saltane.txt";
        final String outputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/output-skazka-o-care-saltane.txt";

        IoSort iosort = new IoSort();
        NioSort niosort = new NioSort();

        try {
            System.out.println(iosort.sort(inputLorem, outputLorem));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(iosort.sort(inputSkazka, outputSkazka));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(niosort.sort(inputSkazka, outputSkazka));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
