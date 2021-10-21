package petscardfile.classes.storages;

import petscardfile.classes.Pet;
import petscardfile.classes.card.CardFile;
import petscardfile.classes.card.PetCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Foo {

    private List<PetCard> petCardList(CardFile cardFile) {
        List<PetCard> petList = new ArrayList<>();
        cardFile...
        for (Map.Entry<UUID, Pet> entry : map.entrySet()) {
            petList.add(new PetCard(entry.getKey(), entry.getValue(), ));
        }
    }



}
