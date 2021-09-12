package task02;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public record FunnyCalendar(LocalDate date) {

    public boolean isWorkingDay() {
        return switch (this.date.getDayOfWeek()) {
            case SATURDAY, SUNDAY -> false;
            default -> true;
        };
    }

    public String itWasDayOfWeek() {
        return this.date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("ru"));
    }



}
