package chat.data;

import lombok.NonNull;

import java.io.OutputStream;
import java.io.PrintStream;

public class LogUnit {

    public static void log(@NonNull OutputStream out, @NonNull Object sourceObj, @NonNull String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sourceObj.getClass().getSimpleName()).append(" > ").append(message);
        LogUnit.log(out, stringBuilder.toString());
    }

    public static void log(@NonNull OutputStream out, @NonNull String message) {
        PrintStream stream = new PrintStream(out);
        stream.println(message);
        stream.flush();
        stream.close();
    }

}
