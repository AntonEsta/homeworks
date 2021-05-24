package lec04.task01;

import java.util.Formatter;
import java.util.Objects;

final class Pet {
    private long id;
    private String nickname;    // кличка
    private float weight;       // вес
    private Person owner;


    /* Конструктор */
    public Pet(String nickname, float weight, Person owner){
        this.nickname = nickname;
        this.weight = weight;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public float getWeight() {
        return weight;
    }

    public Person getOwner() {
        return owner;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    /* Override Methods */

    @Override
    public String toString() {
        Formatter ft = new Formatter();
        ft.format("nickname: %6s; weight: %3.1f; owner: [%s] %n", nickname, weight, owner);
        return ft.toString();
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
