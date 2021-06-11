package lec05.task01.finders.classes;

import lombok.NonNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class FileWordsFinder extends WordsFinder {

    private String getTextFromFile(@NonNull String fileName) throws IOException{
        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(fileName);
             BufferedReader br = new BufferedReader(reader)) {
            int ch;
            while ((ch = br.read()) != -1) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    @Override
    public String[] findAll(@NonNull String fileName) {
        String str = ""; /*TODO: избавиться от <try...catch exception>*/
        try {
            str = getTextFromFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findAll(str);

    }

    @Override
    public Set<String> findAllUnique(@NonNull String fileName) {
//        return super.findAllUnique(getTextFromFile(fileName));
        String str = ""; /*TODO: избавиться от <try...catch exception>*/
        try {
            str = getTextFromFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findAllUnique(str);
    }
}
