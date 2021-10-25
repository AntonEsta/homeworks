package task01;

import java.util.Optional;

public record Person(String name,
                     String surname,
                     Optional<String> patronymic,
                     java.time.LocalDate birthdate,
                     String birthplace) { }
