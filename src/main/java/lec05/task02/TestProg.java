package lec05.task02;


public class TestProg {
    public static void main(String[] args) {

//        final String inputLorem = "/home/esta/IdeaProjects/homeworks/src/main/resources/input.txt";
//        final String outputLorem = "/home/esta/IdeaProjects/homeworks/src/main/resources/output.txt";
//        final String inputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/input-skazka-o-care-saltane.txt";
//        final String outputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/output-skazka-o-care-saltane.txt";

        TextFileGenerator tfg = new TextFileGenerator();

        System.out.println(tfg.textGenerator(3));

    }
}
