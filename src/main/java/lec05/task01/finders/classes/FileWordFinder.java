package lec05.task01.finders.classes;

import lec05.task01.finders.interfaces.Finder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@EqualsAndHashCode
@ToString
public class FileWordFinder implements Finder<String> {

    private final String fileName;
    private final WordFinder finder;

    public FileWordFinder(@NonNull String fileName) {
        this.fileName = fileName;
        finder = new WordFinder(Objects.requireNonNull(getTextFromFile()));
    }

    private String getTextFromFile() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            return reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String find() {
        return finder.find();
    }

    public Stream<String> findAll() {
        return finder.findAll();
    }

//    @Override
//    public LinkedList<String> findAll(@NonNull String fileName) throws IOException {
//        String str = ""; /*TODO: избавиться от <try...catch exception>*/
////        try {
//            str = getTextFromFile(fileName);
//            return super.findAll();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


//    @Override
//    public Set<String> findAllUnique(@NonNull String fileName) {
////        return super.findAllUnique(getTextFromFile(fileName));
//        String str = ""; TODO: избавиться от <try...catch exception>
//        try {
//            str = getTextFromFile(fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return super.findAllUnique(str);
//    }

    /*TODO: to be deleted*/
    public static void main(String[] args) {
        final String inputSkazka = "/home/esta/IdeaProjects/homeworks/src/main/resources/input-skazka-o-care-saltane.txt";
        FileWordFinder fwf = new FileWordFinder(inputSkazka);
        System.out.println(fwf.findAll());
    }

}