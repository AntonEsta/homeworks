package lec05.task01.finders.classes;

import lec05.task01.finders.interfaces.Finder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;


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

    public Collection<String> findAll() {
        return finder.findAll();
    }
}