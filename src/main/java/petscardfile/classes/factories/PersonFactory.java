package petscardfile.classes.factories;

import petscardfile.classes.Person;
import petscardfile.classes.builders.PersonBuilder;
import petscardfile.classes.enums.Sex;

public class PersonFactory {

    public static Person createPerson(String name, String surname, int age, Sex sex) {
        return new PersonBuilder(name, surname, age, sex).build();
    }

    public static Person createPerson(String name, String patronymic, String surname, int age, Sex sex) {
        return new PersonBuilder(name, surname, age, sex).setPatronymic(patronymic).build();
    }

}
