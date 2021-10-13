package petscardfile;

import petscardfile.classes.Person;
import petscardfile.classes.Pet;
import petscardfile.classes.card.CardFile;
import petscardfile.classes.enums.Sex;
import petscardfile.classes.exceptions.DuplicateValueException;
import petscardfile.classes.factories.AbstractFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {


        /* Old
        CardFile cf = new CardFile();
        ArrayList<Pet> pets = new ArrayList<>();
        pets.add(new Pet("Pity", 5.4f, new Person("Dave", 28, Sex.MAN)));
        pets.add(new Pet("Pity", 8.8f, new Person("Dave", 28, Sex.MAN)));
        pets.add(new Pet("Daisy", 3.6f, new Person("Lara", 22, Sex.FEMALE)));
        pets.add(new Pet("Rex", 8.6f, new Person("Jonny", 30, Sex.MAN)));
        pets.add(new Pet("Tom", 3.9f, new Person("Donald", 30, Sex.MAN)));
        pets.add(new Pet("Dolly", 5.1f, new Person("Jerry", 30, Sex.FEMALE)));
        pets.add(new Pet("Archy", 2.6f, new Person("Tom", 30, Sex.MAN))); */


        CardFile cf = AbstractFactory.createCardFile();
        ArrayList<Pet> pets = new ArrayList<>();

        Person owner = AbstractFactory.createPerson("Dave", "Brown", 28, Sex.MAN);
        Pet pet = AbstractFactory.createPet("Pity", 5.4f, owner);
        pets.add(pet);

        owner = AbstractFactory.createPerson("Dave", "Brown", 28, Sex.MAN);
        pet = AbstractFactory.createPet("Pity", 8.8f, owner);
        pets.add(pet);

        owner = AbstractFactory.createPerson("Lara", "White", 22, Sex.FEMALE);
        pet = AbstractFactory.createPet("Daisy", 3.6f, owner);
        pets.add(pet);

        owner = AbstractFactory.createPerson("Jonny", "Red", 30, Sex.MAN);
        pet = AbstractFactory.createPet("Rex", 8.6f, owner);
        pets.add(pet);

        owner = AbstractFactory.createPerson("Donald", "Duck", 30, Sex.MAN);
        pet = AbstractFactory.createPet("Tom", 3.9f, owner);
        pets.add(pet);


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
            System.out.println("Duplicate of record!");
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
