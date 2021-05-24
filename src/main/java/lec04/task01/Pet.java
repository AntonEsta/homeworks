package lec04.task01;

import lombok.*;
import java.util.Formatter;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
final class Pet {

    @NonNull private String nickname;    // кличка
    @NonNull private float weight;       // вес
    @NonNull private Person owner;

    /* Override Methods */

    @Override
    public String toString() {
        Formatter ft = new Formatter();
        ft.format("nickname: %6s; weight: %3.1f; owner: [%s] %n", nickname, weight, owner);
        return ft.toString();
    }
}
