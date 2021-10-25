package petscardfile;

import petscardfile.classes.Person;
import petscardfile.classes.Pet;
import petscardfile.classes.card.CardFile;
import petscardfile.classes.enums.Sex;
import petscardfile.classes.exceptions.DuplicateValueException;
import petscardfile.classes.factories.ObjectFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        CardFile cf = ObjectFactory.createCardFile();
        ArrayList<Pet> pets = new ArrayList<>();

        Person owner = ObjectFactory.createPerson("Dave", "Brown", 28, Sex.MAN);
        Pet pet = ObjectFactory.createPet("Pity", 5.4f, owner);
        pets.add(pet);

        owner = ObjectFactory.createPerson("Dave", "Brown", 28, Sex.MAN);
        pet = ObjectFactory.createPet("Pity", 8.8f, owner);
        pets.add(pet);

        owner = ObjectFactory.createPerson("Lara", "White", 22, Sex.FEMALE);
        pet = ObjectFactory.createPet("Daisy", 3.6f, owner);
        pets.add(pet);

        owner = ObjectFactory.createPerson("Jonny", "Red", 30, Sex.MAN);
        pet = ObjectFactory.createPet("Rex", 8.6f, owner);
        pets.add(pet);

        owner = ObjectFactory.createPerson("Donald", "Duck", 30, Sex.MAN);
        pet = ObjectFactory.createPet("Tom", 3.9f, owner);
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
