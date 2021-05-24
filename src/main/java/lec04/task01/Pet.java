package lec04.task01;

import java.util.Objects;

public class Pet {
    String nickname;    // кличка
    float weight;       // вес

    /* Конструктор */
    public Pet(String nickname, float weight){
        this.nickname = nickname;
        this.weight = weight;
    }

    /* Override Methods */

    @Override
    public String toString() {
        return  "nickname: " + nickname +
                "; weight: " + weight + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Float.compare(pet.weight, weight) == 0 && nickname.equals(pet.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, weight);
    }
}
