package petscardfile;

import petscardfile.classes.Person;
import petscardfile.classes.Pet;
import petscardfile.classes.card.CardFile;
import petscardfile.classes.enums.Sex;
import petscardfile.classes.factories.ObjectFactory;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        CardFile cardFile = ObjectFactory.createCardFile();

        Person owner = ObjectFactory.createPerson("Dave", "Brown", 28, Sex.MAN);
        Pet pet = ObjectFactory.createPet("Pity", 5.4f);

        cardFile.addPet(pet, owner);

        owner = ObjectFactory.createPerson("Dave", "Brown", 28, Sex.MAN);
        pet = ObjectFactory.createPet("Pity", 8.8f);
        cardFile.addPet(pet, owner);

        owner = ObjectFactory.createPerson("Lara", "White", 22, Sex.FEMALE);
        pet = ObjectFactory.createPet("Daisy", 3.6f);
        cardFile.addPet(pet, owner);

        owner = ObjectFactory.createPerson("Jonny", "Red", 30, Sex.MAN);
        pet = ObjectFactory.createPet("Rex", 8.6f);
        cardFile.addPet(pet, owner);

        owner = ObjectFactory.createPerson("Donald", "Duck", 30, Sex.MAN);
        pet = ObjectFactory.createPet("Tom", 3.9f);
        cardFile.addPet(pet, owner);

        Map<UUID, Pet> map = cardFile.findPet("Rex");
        System.out.println(map);

        List<Pet> petList = cardFile.sortedPetList();

        System.out.println(petList);

        cardFile.removePet(pet);

    }

}
