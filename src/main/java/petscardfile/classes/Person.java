package petscardfile.classes;

import lombok.*;
import petscardfile.classes.builders.PersonBuilder;
import petscardfile.classes.enums.Sex;

@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Person {
    @NonNull private String name;
    @NonNull private String surname;
    private String patronymic;
    private int age;
    @NonNull private Sex sex;

    public Person(PersonBuilder builder) {
        this.name = builder.getName();
        this.surname = builder.getSurname();
        this.patronymic = getPatronymic();
        this.age = builder.getAge();
        this.sex = builder.getSex();
    }

}
