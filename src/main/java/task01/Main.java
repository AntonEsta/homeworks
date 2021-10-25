package task01;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        // work with records
//        var person = new task01.Person("Антон", "Эста", Optional.of("Викторович"), LocalDate.of(1985, 5, 10), "Красноярск-45");
        var person = new Person("Антон", "Эста", Optional.empty(), LocalDate.of(1985, 5, 10), "Красноярск-45");
        var insurance = new Insurance(12687894523L, person);
        insurance.printMandatory();

        // work with NPE
        Objects.requireNonNull(null);

    }

}
