package lec05.task01;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@UtilityClass
public class FileSorter {

    public StatFinder ioSort(@NonNull String inputFileName, @NonNull String outputFileName) throws IOException {

        int foundWords = 0;
        HashSet<String> words = new HashSet<>();
        char[] latCharTable = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz").toCharArray();
        char[] kirCharTable = ("ЁАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюяё").toCharArray();

        try (FileReader reader = new FileReader(inputFileName);
             BufferedReader br = new BufferedReader(reader)) {
            StringBuilder str = new StringBuilder();
            int ch;
            while ((ch = br.read()) != -1) {
                if (Arrays.binarySearch(latCharTable, (char) ch) >= 0 ||
                        Arrays.binarySearch(kirCharTable, (char) ch) >= 0) {
                    str.append((char) ch);
                } else {
                    if (str.toString().isEmpty()) continue;
                    foundWords++;
                    words.add(str.toString().toLowerCase());
                    str = new StringBuilder();
                }
            }
        }

        try (FileWriter writer = new FileWriter(outputFileName);
             BufferedWriter bw = new BufferedWriter(writer)) {
            Stream<String> stream = words.stream().sorted(Comparator.naturalOrder());
            stream.forEach((s) -> {
                try {
                    bw.write(s);
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return new StatFinder(foundWords, words.size());
    }

    public StatFinder nioSort(@NonNull String inputFileName, @NonNull String outputFileName) throws IOException {

        int foundWords = 0;
        HashSet<String> words = new HashSet<>();
        char[] latCharTable = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz").toCharArray();
        char[] kirCharTable = ("ЁАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюяё").toCharArray();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFileName))) {
            StringBuilder str = new StringBuilder();
            int ch;
            while ((ch = br.read()) != -1) {
                if (Arrays.binarySearch(latCharTable, (char) ch) >= 0 ||
                        Arrays.binarySearch(kirCharTable, (char) ch) >= 0) {
                    str.append((char) ch);
                } else {
                    if (str.toString().isEmpty()) continue;
                    foundWords++;
                    words.add(str.toString().toLowerCase());
                    str = new StringBuilder();
                }
            }
        }

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(outputFileName))) {
            Stream<String> stream = words.stream().sorted(Comparator.naturalOrder());
            stream.forEach((s) -> {
                try {
                    bw.write(s);
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return new StatFinder(foundWords, words.size());
    }

}
