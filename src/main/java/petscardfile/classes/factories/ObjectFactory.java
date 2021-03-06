package petscardfile.classes.factories;

import petscardfile.classes.*;
import petscardfile.classes.card.CardFile;
import petscardfile.classes.enums.Sex;

public class ObjectFactory {

    public static Pet createPet(String nickname, float weight) {
        return PetFactory.createPet(nickname, weight);
    }

    public static Person createPerson(String name, String surname, int age, Sex sex) {
        return PersonFactory.createPerson(name, surname, age, sex);
    }

    public static CardFile createCardFile() {
        return CardFile.getInstance();
    }

}
