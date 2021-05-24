package lec04.task01;

import java.util.Objects;

public class Card {
    Pet pet;
    Person owner;

    /* Конструкторы */
    public Card(Pet pet, Person owner) {
        initialization(pet, owner);
    }

    public Card(String nickname, float weight, Person owner) {
        Pet pet = new Pet(nickname, weight);
        initialization(pet, owner);
    }

    public Card(String nickname, float weight, String ownerName, int ownerAge, Sex ownerSex){
        Person owner = new Person(ownerName, ownerAge, ownerSex);
        Pet pet = new Pet(nickname, weight);
        initialization(pet, owner);
    }

    private void initialization(Pet pet, Person owner){
        this.pet = pet;
        this.owner = owner;
    }

    /* Override Methods */

    @Override
    public String toString() {
        return  "pet => " + pet +
                "owner => " + owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return pet.equals(card.pet) && owner.equals(card.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pet, owner);
    }
}
