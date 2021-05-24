package lec04.task01;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс выполняет роль картотеки домашних питомцев.
 */
final class CardFile {

    private TreeMap<Long, Pet> pets = new TreeMap<>();

    public Pet getPet(long id) {
        return pets.get(id);
    }

    /**
     * Генерирует новый Id для карты.
     * @param pet новая карта которую необходимо добавить.
     * @return значение нового Id.
     */
    private Long generatorId(Pet pet) throws Exception {
        long id;
        try {
            if (pets.isEmpty()) {
                id = 1L;
            } else {
                if (pets.containsValue(pet)) {
                    throw new Exception("Attempt to add a duplicate record!");
                }
                id = pets.lastKey() + 1;
            }
        } catch (NullPointerException e) {
            id = 1L;
        }
        return id;
    }

    /**
     * Добавляет новую карту питомца в картотеку.
     * @param pet карта для добавления в картотеку.
     * @return значение ID добавленной карты питомца.
     */
    public Long addPet(Pet pet) throws Exception {
        long id = generatorId(pet);
        pets.put(id, pet);
        return id;
    }

    /**
     * Обновляет информацию о питомце по его id.
     * @param id имя код питомца {@code long}
     * @param pet информация о питомце питомце {@code Pet}
     * @return объект питомец {@code Pet}.
     */
    public Pet setPet(long id, Pet pet) {
        return pets.put(id, pet);
    }

    /**
     * Удаляет карту питомца.
     * @param id номер (ID) карты питомца.
     * @return возвращает {@code true} при успешном удалении карты.
     */
    public boolean deletePet(Long id) {
        return pets.remove(id) != null;
    }

    /**
     * Поиск карт по кличке питомца.
     * @param nickname кличка питомца.
     * @return картотека {@code CardFile} с картами найденных питомцев.
     */
    public TreeMap<Long, Pet> findPet(String nickname){
        TreeMap<Long, Pet> pt = new TreeMap<>();
        if (pets.isEmpty()) return pt;
        Map<Long, Pet> map = pets.entrySet().stream()
                .filter(e -> e.getValue().getNickname().equals(nickname))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        pt.putAll(map);
        return pt;
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
                    if (result == 0){
                        result = p.getNickname().compareToIgnoreCase(o.getNickname());
                        if (result == 0) {
                            result = Float.compare(p.getWeight(), o.getWeight()) * -1;
                        }
                    }
                    return result;
                })
                .collect(Collectors.toList());
    }

    /* Override Methods */

    @Override
    public String toString() {
        Iterator<Map.Entry<Long, Pet>> iterator = pets.entrySet().iterator();
        StringBuilder str = new StringBuilder("CardFile include ...\n");
        while (iterator.hasNext()) {
            str.append(iterator.next()).append("\n");
        }
        return str.toString();
    }
}
