package petscardfile.classes;

import lombok.*;
import petscardfile.classes.enums.Sex;

@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Person {
    @NonNull private final String name;
    @NonNull private final String surname;
    private String patronymic;
    private int age;
    @NonNull private  final Sex sex;

    private Person(Builder builder) {
        this.name = builder.getName();
        this.surname = builder.getSurname();
        this.patronymic = getPatronymic();
        this.age = builder.getAge();
        this.sex = builder.getSex();
    }

    @Getter
    public static class Builder {

        @NonNull
        private final String name;
        @NonNull
        private final String surname;
        private String patronymic;
        private final int age;
        @NonNull
        private final Sex sex;

        public Builder(String name, String surname, int age, Sex sex) {
            this.name = name;
            this.surname = surname;
            this.age = age;
            this.sex = sex;
        }

        public Builder setPatronymic(String s) {
            this.patronymic = s;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

}
