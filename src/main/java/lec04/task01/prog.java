package lec04.task01;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class prog {

    public static void main(String[] args) {


        CardFile cf = new CardFile();
        ArrayList<Pet> pets = new ArrayList<>();
        pets.add(new Pet("Pity", 5.4f, new Person("Dave", 28, Sex.MAN)));
        pets.add(new Pet("Pity", 8.8f, new Person("Dave", 28, Sex.MAN)));
        pets.add(new Pet("Daisy", 3.6f, new Person("Lara", 22, Sex.FEMALE)));
        pets.add(new Pet("Rex", 8.6f, new Person("Jonny", 30, Sex.MAN)));
        pets.add(new Pet("Tom", 3.9f, new Person("Donald", 30, Sex.MAN)));
        pets.add(new Pet("Dolly", 5.1f, new Person("Jerry", 30, Sex.FEMALE)));
        pets.add(new Pet("Archy", 2.6f, new Person("Tom", 30, Sex.MAN)));
        /* Добавление питомцев */
        try {

            for (Pet p : pets) {
                cf.addPet(p);
            }

        } catch (Exception e) {
            System.out.printf("Generated exception: %s", e);
        }

        /* Exception demo */

        System.out.println("Exception demo!");

        try {
            cf.addPet(pets.get(0));
        } catch (DuplicateValueException e) {
            e.printStackTrace();
        }

        System.out.println(cf);

        /* Delete pet by id. */
        for (Map.Entry<UUID, Pet> entry : Objects.requireNonNull(cf.findPet("Pity")).entrySet()) {
            UUID k = entry.getKey();
            System.out.println("Delete Pet:  " + cf.deletePet(k));
        }

        /* Find pet by nickname. */
        System.out.printf("Find pet by nickname \"Pity\"...%n %s %n%n", cf.findPet("Pity"));

        /* Get sorted list of pets. */
        System.out.println("Sorted list...\n" + cf.sortedPetList());

    }

}
