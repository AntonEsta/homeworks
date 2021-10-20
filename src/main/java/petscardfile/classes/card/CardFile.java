package petscardfile.classes.card;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import petscardfile.classes.Person;
import petscardfile.classes.Pet;
import petscardfile.classes.storages.*;
import petscardfile.classes.storages.ifce.IndexedTypedStorage;
import petscardfile.classes.storages.impl.RelationStorage;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Класс выполняет роль картотеки домашних питомцев.
 */

@ToString
@EqualsAndHashCode
@FieldDefaults(level=AccessLevel.PRIVATE)
public class CardFile {

    static final IndexedTypedStorage<UUID, Person> personStorage    = StorageFactory.newPersonStorage();
    static final IndexedTypedStorage<UUID, Pet> petStorage    = StorageFactory.newPetStorage();
    static final RelationStorage relationsStorage   = StorageFactory.newRelationStorage();

    static volatile CardFile cardFile = null;

    private CardFile(){}

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
    public PetCard addPet(@NonNull Pet pet, @NonNull Person owner) {
        UUID petId = petStorage.add(pet);
        UUID ownerId = personStorage.add(owner);
        relationsStorage.add(petId, ownerId);
        PersonCard ownerCard = new PersonCard(ownerId, owner);
        return new PetCard(petId, pet, ownerCard);
    }

    /** Удаляет карту питомца.
     * @param pet питомец.
     */
    public void removePet(Pet pet) {
        UUID petId = petStorage.getIndex(pet);
        petStorage.remove(petId);
        relationsStorage.remove(petId);
    }

    /**
     * Поиск питомца(ев) по его кличке.
     * @param nickname кличка питомца.
     * @return {@link CardFile} найденных питомцев или {@code null} если негде искать.
     */
    public Map<@NonNull UUID, Pet> findPet(String nickname) {
        Map<UUID, Pet> petsMap = petStorage.toMap();
        if (petsMap.isEmpty()) return null;
        return petsMap.entrySet().stream()
                .filter(e -> e.getValue().getNickname().equals(nickname))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Person getOwnerByPet(Pet pet) {
        try {
            final UUID petId = petStorage.getIndex(pet);
            return personStorage.get(petId);
        } catch (Exception e) {
            return null;
        }
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
        Map<UUID, Pet> pets = petStorage.toMap();
        return pets.values().stream()
                .sorted((p, o) -> {
                    final Person owner1 = getOwnerByPet(p);
                    final Person owner2 = getOwnerByPet(o);
                    if (owner1 == null || owner2 == null) return 0;
                    int result = owner1.getName().compareToIgnoreCase(owner2.getName());
                    if (result != 0) return result;
                    result = p.getNickname().compareToIgnoreCase(o.getNickname());
                    if (result != 0) return result;
                    result = Float.compare(p.getWeight(), o.getWeight()) * -1;
                    return result;
                })
                .collect(Collectors.toList());
    }

}
