import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SomeClassSourceFileCreator implements Creator {

    private final String someClassFileName = "SomeClass.java";

    private final static byte[] content;

    static {
                content = "public class SomeClass implements Worker{ public void doWork(){}}".getBytes();
    }

    /**
     * Creates "SomeClass.java" file.
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    private void create0() throws IOException {
        String dir = "/home/esta/IdeaProjects/homeworks/src/main/java";
        prepareFile(dir);
        String methodBody = getMethodBodyFromConsole();
        if (methodBody.length() == 0) return;
        String someClassSourceCode = readJavaFile(dir);
        if (someClassSourceCode.length() == 0) return;
        someClassSourceCode = addMethodBody(methodBody, someClassSourceCode);
        writeSourceFile(dir, someClassSourceCode);
    }

    @SneakyThrows
    private void prepareFile(@NonNull String directory) {
        Path path = Paths.get(directory, someClassFileName);
        Files.write(path, content);
    }

    /**
     * Adds the method body to doWork() method.
     * @param methodBody Content string.
     * @param someClassSourceCode Class content.
     */
    private String addMethodBody(@NonNull String methodBody,@NonNull String someClassSourceCode) {
        Pattern pattern = Pattern.compile("public\\svoid\\sdoWork\\(\\)\\s*\\{");
        Matcher matcher = pattern.matcher(someClassSourceCode);
        if (matcher.find()) {
            int endOfMethodDeclare = matcher.end();
            StringBuilder stringBuilder = new StringBuilder(someClassSourceCode);
            stringBuilder.insert(endOfMethodDeclare, methodBody);
            return stringBuilder.toString();
        } else {
            throw new RuntimeException("The method 'doWork()' not find in file. (Code should not require importing additional classes)");
        }
    }

    /**
     * Rewrites "SomeClass.java" file
     * @param directory Path where is consider source file "SomeClass.java"
     * @param someClassSourceCode Class content.
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    private void writeSourceFile(@NonNull String directory, @NonNull String someClassSourceCode) throws IOException {
        Path path = Paths.get(directory, someClassFileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.WRITE)) {
            writer.write(someClassSourceCode, 0, someClassSourceCode.length());
        }
    }

    /**
     * Reads "SomeClass.java" file
     * @param directory Path where is consider source file "SomeClass.java"
     * @return "SomeClass.java" file content.
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    private String readJavaFile(@NonNull String directory) throws IOException {
        Path path = Paths.get(directory, someClassFileName);
        String str;
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            str = reader.lines().collect(Collectors.joining());
        }
        return str;
    }


    /**
     * Gets the method body from console.
     * @return String of the method body.
     */
    private String getMethodBodyFromConsole() {
        Scanner scanner = new Scanner(System.in);
        String inStr;
        StringBuilder buff = new StringBuilder();
        System.out.println("Write the body of class method ( SomeClass.doWork() ).");
        while (!(inStr = scanner.nextLine()).equals("")) {
            buff.append(inStr);
        }
        return buff.toString();
    }

    @Override
    public void create() {
        try {
            create0();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
