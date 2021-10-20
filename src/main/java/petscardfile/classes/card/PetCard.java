package petscardfile.classes.card;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import petscardfile.classes.Pet;

import java.util.UUID;

@Getter
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class PetCard {

    final UUID id;
    final Pet pet;
    final PersonCard owner;

}
