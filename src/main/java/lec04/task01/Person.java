package lec04.task01;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
class Person {
    @NonNull private String name;
    @NonNull private int age;
    @NonNull private Sex sex;
}
