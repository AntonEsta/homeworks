package petscardfile.classes.factories;

import petscardfile.classes.Person;
import petscardfile.classes.enums.Sex;

public class PersonFactory {

    public static Person createPerson(String name, String surname, int age, Sex sex) {
        return new Person.Builder(name, surname, age, sex).build();
    }

    public static Person createPerson(String name, String patronymic, String surname, int age, Sex sex) {
        return new Person.Builder(name, surname, age, sex).setPatronymic(patronymic).build();
    }

}
