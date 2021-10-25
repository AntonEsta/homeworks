package task02;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public final class Historian {

    private static LocalDate getLocalDateFromConsole() {
        System.out.println("Пожалуйста, введите дату вышего рождения (ДД-ММ-ГГГГ):");
        final Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            var str = scanner.nextLine();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                return LocalDate.parse(str, formatter);
            } catch (DateTimeParseException e) {
                if (str.isEmpty()) throw new RuntimeException("Is empty string.");
                System.out.println("Wrong date format. Try again.");
            }
        }
        throw new RuntimeException();
    }

    public static void printOnWhatDayOfTheWeekIWasBorn() {
        FunnyCalendar calendar;
        try {
            calendar = new FunnyCalendar(getLocalDateFromConsole());
        } catch (Throwable e) {
            return;
        }
        final String formattedDate = calendar.date().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru")));
        final String weekday = calendar.isWorkingDay() ? "будний" : "выходной";
        final String dayOfWeek = calendar.itWasDayOfWeek();
        System.out.printf("Вы роделись %s в %s день.\nДень недели: %s.", formattedDate, weekday, dayOfWeek);
    }

}
