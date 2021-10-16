package petscardfile.classes.card;

import lombok.*;
import lombok.experimental.FieldDefaults;
import petscardfile.classes.Person;
import petscardfile.classes.Pet;
import petscardfile.classes.exceptions.DuplicateValueException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Класс выполняет роль картотеки домашних питомцев.
 */

@ToString
@EqualsAndHashCode
@FieldDefaults(level=AccessLevel.PRIVATE)
public class CardFile {

    private static volatile CardFile cardFile = null;

    final ConcurrentHashMap<@NonNull UUID, Pet> pets = new ConcurrentHashMap<>();

    CardFile(){}

    /**
     * Return class instance.
     * @return singleton class instance.
     */
    public static synchronized CardFile getInstance(){
        CardFile instance = cardFile;
        if (instance == null) {
            synchronized (CardFile.class) {
                instance = cardFile;
                if (instance == null) {
                    cardFile = instance = new CardFile();
                }
            }
        }
        return instance;
    }

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
    @SuppressWarnings("UnusedDeclaration")
    public UUID addPet(@NonNull String nickname, float weight, @NonNull Person owner) throws Exception {
        Pet pet = new Pet(nickname, weight, owner);
        return addPet(pet);
    }

    /** пересмотр кода согласно коментариям
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
    public Map<@NonNull UUID, Pet> findPet(String nickname){
        if (pets.isEmpty()) return null;
        return pets.entrySet().stream()
                .filter(e -> e.getValue().getNickname().equals(nickname))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Выводит на экран список животных в отсортированном порядке.
     * Поля для сортировки – хозяин (имя, по возрастанию),
     * если имена хозяев одинаковые - кличка животного.
     * Если и имя хозяина и кличка животного одинаковые -
     * раньше выводит животное, у которого больше вес.
     * @return список животных в отсортированном порядке.
     */
    public List<Pet> sortedPetList(){
        return pets.values().stream()
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
