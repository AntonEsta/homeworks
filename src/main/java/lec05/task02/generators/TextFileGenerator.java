package lec05.task02.generators;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
*  Class - generator of text files
* */
@EqualsAndHashCode
@ToString
public class TextFileGenerator {

    /**
     * Writes string to file.
     *
     * @param path Input file path.
     * @param fileSize Size of each output file.
     * @param words Array of strings {@link String} to upload to file.
     * @param marker Marker to the start index of word.
     * */
    private int writeStringsToFile(Path path, int fileSize, String[] words, int marker) throws IOException {

        if (marker < 0 || marker >= words.length) throw new ArrayIndexOutOfBoundsException();

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            while (Files.size(path) < fileSize) {
                bw.write(words[marker++]);
                if (marker == words.length) marker = 0;
            }
        }
        return marker;
    }

    /**
     * File generation
     *
     * @param path Input file path
     * @param n Number of output files
     * @param size Size of each output file
     * @param words Array of strings {@link String} to upload to file
     */
    public void getFile(@NonNull String path, int n, int size, String[] words) throws IOException {
        int counterFiles = 0;
        int mark = 0;
        String fileExtension = ".txt";
        String subdir = "/out";
        Path pPath;
        while (counterFiles < n) {
            pPath = Paths.get(path, subdir +  (counterFiles + 1) + fileExtension);
            mark = writeStringsToFile(pPath, size, words, mark);
            counterFiles++;
        }
    }
}
