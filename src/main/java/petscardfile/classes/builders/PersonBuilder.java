package petscardfile.classes.builders;

import lombok.Getter;
import lombok.NonNull;
import petscardfile.classes.Person;
import petscardfile.classes.enums.Sex;

@Getter
public class PersonBuilder {

    @NonNull private final String name;
    @NonNull private final String surname;
    private String patronymic;
    private final int age;
    @NonNull private final Sex sex;

    public PersonBuilder(String name, String surname, int age, Sex sex){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.sex = sex;
    }

    public PersonBuilder setPatronymic(String s) {
        this.patronymic = s;
        return this;
    }

    public Person build() {
        return new Person(this);
    }
}
