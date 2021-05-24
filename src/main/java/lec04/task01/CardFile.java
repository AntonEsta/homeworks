package lec04.task01;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс выполняет роль картотеки домашних питомцев.
 */

@ToString
@EqualsAndHashCode
@FieldDefaults(level=AccessLevel.PRIVATE)
public class CardFile {

    /*TODO: >? При использовании lombok.var IDE требует кастовать HashMap!
    * TODO     private var pets = (var) new TreeMap<UUID, Pet>();
    *  */
//    private var pets = (var) new TreeMap<UUID, Pet>();
    final HashMap<@NonNull UUID, Pet> pets = new HashMap<>();

    /**
     * Добавляет новую карту питомца в картотеку.
     * @param pet карта для добавления в картотеку.
     * @return значение {@link UUID} добавленной карты питомца или {@code null} при не удачном добавлении.
     */
    public UUID addPet(@NonNull Pet pet) throws DuplicateValueException {
        if (!pets.isEmpty() & pets.containsValue(pet)) throw new DuplicateValueException();
        UUID id;
        do {
            id = UUID.randomUUID();
        } while (pets.containsKey(id));
        return (pets.put(id, pet) != null) ? id : null;
    }

    /**
     * Добавляет новую карту питомца в картотеку.
     * @param nickname имя питомца типа {@code String}
     * @param weight вес питомца типа {@code float}
     * @param owner владельца питомца типа {@code Person}
     * @return значение ID добавленной карты питомца.
     */
    public UUID addPet(@NonNull String nickname, @NonNull float weight, @NonNull Person owner) throws Exception {
        Pet pet = new Pet(nickname, weight, owner);
        return addPet(pet);
    }

    /**
     * Удаляет карту питомца.
     * @param id номер {@link UUID} питомца.
     * @return возвращает {@code true} при успешном удалении карты.
     */
    public boolean deletePet(UUID id) {
        return pets.remove(id) != null;
    }

    /**
     * Поиск питомца(ев) по его кличке.
     * @param nickname кличка питомца.
     * @return {@link CardFile} найденных питомцев или {@code null} если негде искать.
     */
    public HashMap<@NonNull UUID, Pet> findPet(String nickname){
        if (pets.isEmpty()) return null;
        Map<@NonNull UUID, Pet> map = pets.entrySet().stream()
                .filter(e -> e.getValue().getNickname().equals(nickname))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new HashMap<>(map);
    }

    /**
     * Выводит на экран список животных в отсортированном порядке.
     * Поля для сортировки – хозяин (имя, по возрастанию),
     * если имена хозяев одинаковые - кличка животного.
     * Если и имя хозяина и кличка животного одинаковые -
     * раньше выводит животное, у которого больше вес.
     * @return список животных в отсортированном порядке.
     */
    public ArrayList<Pet> sortedPetList(){
        return (ArrayList<Pet>) pets.values().stream()
                .sorted((p, o) -> {
                    int result = p.getOwner().getName().compareToIgnoreCase(o.getOwner().getName());
                    if (result != 0) return result;
                    result = p.getNickname().compareToIgnoreCase(o.getNickname());
                    if (result != 0) return result;
                    result = Float.compare(p.getWeight(), o.getWeight()) * -1;
                    return result;
                })
                .collect(Collectors.toList());
    }

}
