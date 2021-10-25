package task01;

import lombok.NonNull;

import java.lang.reflect.Field;
import java.time.format.TextStyle;
import java.util.Locale;

public record Insurance(long id, @NonNull Person person) {

    public String idToString() {
        var out = new StringBuilder();
        final var idStr = String.valueOf(this.id);
        out.append(idStr, 0, 3);
        out.append('-');
        out.append(idStr, 3, 6);
        out.append('-');
        out.append(idStr, 6, 9);
        out.append(' ');
        out.append(idStr, 9, 11);
        return out.toString();
    }

    public void printMandatory(){
        var lineLength = 37;
        final String titleRF = "Российская Федерация";
        final String titleInsurance = "СТРАХОВОЕ СВИДЕТЕЛЬСТВО";
        final String titleSubIns = "собязательного пенсионного страхования";
        final String titleDatePlace = "Дата и место рождения";
        final String titleFIO = "Ф.И.О.";
        var personFields = this.person.getClass().getDeclaredFields();
        for (Field personField : personFields) {
            if (personField.toString().length() > lineLength) {
                lineLength = personField.toString().length();
            }
        }
        System.out.println();
        printStringToCenter(titleRF, lineLength);
        printStringToCenter(titleInsurance, lineLength);
        printStringToCenter(titleSubIns, lineLength);
        printStringToCenter(this.idToString(), lineLength);
        System.out.printf("%s\t%s%n", titleFIO, this.person.surname());
        System.out.printf("\t\t%s%n", this.person.name());
        System.out.printf("\t\t%s%n", this.person.patronymic().orElse(""));
        System.out.println(titleDatePlace);
//        System.out.printf("\t\t%s%n", this.person.birthdate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        System.out.printf("\t\t%d %s %s%n", this.person.birthdate().getDayOfMonth(),
                                            this.person.birthdate().getMonth().getDisplayName(TextStyle.FULL, new Locale("ru")),
                                            this.person.birthdate().getYear());
        System.out.printf("\t\t%s%n", this.person.birthplace());
        System.out.println();
    }

    private int getCenter(int lineLength, @NonNull String string) {
        return (lineLength - string.length()) / 2;
    }

    private void printStringToCenter(String str, int lineLength) {
        String outStr = " ".repeat(getCenter(lineLength, str)) + str;
        System.out.println(outStr);
    }

}
