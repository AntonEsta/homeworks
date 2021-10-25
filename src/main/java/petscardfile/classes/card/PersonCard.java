package petscardfile.classes.card;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import petscardfile.classes.Person;

import java.util.UUID;

@Getter
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class PersonCard {

    final UUID id;
    final Person person;

}
