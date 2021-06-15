package lec05.task01;

import lec05.task01.sorters.classes.IoFileSorter;
import lec05.task01.sorters.classes.NioFileSorter;

import java.io.IOException;

public class TestProg {
    public static void main(String[] args) {

        final String inputLorem = "/home/esta/IdeaProjects/homeworks/src/main/resources/input.txt";
        final String outputLorem = "/home/esta/IdeaProjects/homeworks/src/main/resources/output.txt";
        final String inputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/input-skazka-o-care-saltane.txt";
        final String outputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/output-skazka-o-care-saltane.txt";

        IoFileSorter iosort = new IoFileSorter();
        NioFileSorter niosort = new NioFileSorter();

        System.out.println("IoFileSorter Class...");
//            NioFileSorter niosortort = new NioFileSorter();
        iosort.sort(inputLorem, outputLorem);
//            System.out.println("Lorem text: " + iosort.sort(inputLorem, outputLorem));
        iosort.sort(inputSkazka, outputSkazka);
//            System.out.println("Skazka text: " + iosort.sort(inputSkazka, outputSkazka));
        System.out.println("NioFileSorter Class...");
        niosort.sort(inputSkazka, outputSkazka);
//            System.out.println("Skazka text: " + niosort.sort(inputSkazka, outputSkazka));

    }
}
