package lec05.task01.finders.classes;

import lec05.task01.data.CharacterTables;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class FileWordsFinder extends WordsFinder {

    /* TODO: рассмотреть вариант с 'WordsFinder wordsFinder = new WordsFinder()' */

    @Override
    public List<String> findAll(String fileName) {
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
        return super.findAll(sb.toString());
    }

    @Override
    public Set<String> findAllUnique(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(fileName);
             BufferedReader br = new BufferedReader(reader)) {
            int ch;
            while ((ch = br.read()) != -1) {
                sb.append((char) ch);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return super.findAllUnique(sb.toString());
    }
}
