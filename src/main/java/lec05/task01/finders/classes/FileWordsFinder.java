package lec05.task01.finders.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class FileWordsFinder extends WordsFinder {

    /* TODO: рассмотреть вариант с 'WordsFinder wordsFinder = new WordsFinder()' */

    private String getTextFromFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(fileName);
             BufferedReader br = new BufferedReader(reader)) {
            int ch;
            while ((ch = br.read()) != -1) {
                sb.append(ch);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return sb.toString();
    }

    @Override
    public String[] findAll(String fileName) {
        return super.findAll(getTextFromFile(fileName));
    }

    @Override
    public Set<String> findAllUnique(String fileName) {
        return super.findAllUnique(getTextFromFile(fileName));
    }
}
