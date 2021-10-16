package petscardfile.classes.factories;

import lombok.NonNull;
import petscardfile.classes.Person;
import petscardfile.classes.Pet;
import petscardfile.classes.enums.Sex;

public class PetFactory {

    public static Pet createPet(@NonNull String nickname, float weight, @NonNull Person owner) {
        return new Pet(nickname, weight, owner);
    }

    public static Pet createPet(@NonNull String nickname, float weight, @NonNull String name, @NonNull String surname, String patronymic, int age, @NonNull Sex sex) {
        Person owner = ObjectFactory.createPerson(name, patronymic, surname, age, sex);
        return new Pet(nickname, weight, owner);
    }

}
