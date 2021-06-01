package lec05.task01;

import java.io.IOException;

public class TestProg {
    public static void main(String[] args) {

        final String inputLorem = "/home/esta/IdeaProjects/homeworks/src/main/resources/input.txt";
        final String outputLorem = "/home/esta/IdeaProjects/homeworks/src/main/resources/output.txt";
        final String inputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/input-skazka-o-care-saltane.txt";
        final String outputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/output-skazka-o-care-saltane.txt";

        try {
            System.out.println(FileSorter.ioSort(inputLorem, outputLorem));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(FileSorter.ioSort(inputSkazka, outputSkazka));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(FileSorter.nioSort(inputSkazka, outputSkazka));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
