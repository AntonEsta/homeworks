package petscardfile.classes.factories;

import lombok.NonNull;
import petscardfile.classes.Pet;

public class PetFactory {

    public static Pet createPet(@NonNull String nickname, float weight) {
        return new Pet(nickname, weight);
    }

}
