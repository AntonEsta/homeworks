package lec05.task01;

import lec05.task01.sorters.classes.IoFileSorter;
import lec05.task01.sorters.classes.NioFileSorter;
import lec05.task01.sorters.interfaces.FileSorter;

public class TestProg {
    public static void main(String[] args) {

        final String inputLorem = "/home/esta/IdeaProjects/homeworks/src/main/resources/input.txt";
        final String outputLorem = "/home/esta/IdeaProjects/homeworks/src/main/resources/output.txt";
        final String inputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/input-skazka-o-care-saltane.txt";
        final String outputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/output-skazka-o-care-saltane.txt";

        FileSorter iosort = new IoFileSorter();
        FileSorter niosort = new NioFileSorter();

        System.out.println("IoFileSorter Class...");
        iosort.sort(inputLorem, outputLorem);
        iosort.sort(inputSkazka, outputSkazka);

        System.out.println("NioFileSorter Class...");
        niosort.sort(inputSkazka, outputSkazka);

    }
}
