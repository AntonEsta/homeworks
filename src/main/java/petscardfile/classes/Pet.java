package petscardfile.classes;

import lombok.*;
import java.util.Formatter;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public final class Pet {

    @NonNull private String nickname;    // кличка
    private float weight;       // вес
    @NonNull private Person owner;

    /* Override Methods */

    @Override
    public String toString() {
        Formatter ft = new Formatter();
        ft.format("nickname: %6s; weight: %3.1f; owner: [%s] %n", nickname, weight, owner);
        return ft.toString();
    }

}
