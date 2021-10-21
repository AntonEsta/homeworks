package petscardfile.classes;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public final class Pet {

    @NonNull private String nickname;    // кличка
    private float weight;       // вес

    /* Override Methods */

    @Override
    public String toString() {
        return String.format("nickname: %6s; weight: %3.1f", nickname, weight);
    }

}
