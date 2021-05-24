package lec04.task01;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Класс выполняет роль картотеки домашних питомцев.
 */
public class CardFile extends TreeMap<Long, Card> {

    /**
     * Генерирует новый Id для карты.
     * @param newCard новая карта которую необходимо добавить.
     * @return значение нового Id.
     */
    private Long generatorId(Card newCard) throws Exception {
        long id;
        if (isEmpty()) {
            id = 1L;
        } else {
            if (containsValue(newCard)) {
                throw new Exception("Attempt to add a duplicate record!");
            }
            id = lastKey() + 1;
        }
        return id;
    }

    /**
     * Добавляет новую карту питомца в картотеку.
     * @param card карта для добавления в картотеку.
     * @return значение ID добавленной карты питомца.
     */
    public Long addCard(Card card) throws Exception {
        long id = generatorId(card);
        put(id, card);
        return id;
    }

    /**
     * Добавляет новую карту питомца в картотеку.
     * @param petNickname имя питомца типа {@code String}
     * @param petWeight вес питомца типа {@code float}
     * @param owner владельца питомца типа {@code Person}
     * @return значение ID добавленной карты питомца.
     */
    public Long addCard(String petNickname, float petWeight, Person owner) throws Exception {
        Card card = new Card(petNickname, petWeight, owner);
        return addCard(card);
    }

    /**
     * Добавляет новую карту питомца в картотеку.
     * @param petNickname имя питомца типа {@code String}.
     * @param petWeight вес питомца типа {@code float}.
     * @param ownerName имя владельца типа {@code String}.
     * @param ownerAge возраст владельца типа {@code int}.
     * @param ownerSex пол вдадельца типа {@code Person.Sex}.
     * @return значение ID добавленной карты питомца.
     */
    public Long addCard(String petNickname, float petWeight, String ownerName, int ownerAge, Sex ownerSex) throws Exception {
        Card card = new Card(petNickname, petWeight, ownerName, ownerAge, ownerSex);
        return addCard(card);
    }

    /**
     * Удаляет карту питомца.
     * @param Id номер (ID) карты питомца.
     * @return возвращает {@code true} при успешном удалении карты.
     */
    public boolean deleteCard(Long Id) {
        return remove(Id) != null;
    }

    /**
     * Поиск карт по кличке питомца.
     * @param petNickname кличка питомца.
     * @return картотека {@code CardFile} с картами найденных питомцев.
     */
    public CardFile findCard(String petNickname){
        CardFile cf = new CardFile();
        if (isEmpty()) return cf;
        Map<Long, Card> map = entrySet().stream()
                .filter(e -> e.getValue().pet.nickname.equals(petNickname))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        cf.putAll(map);
        return cf;
    }

    /**
     * Выводит на экран список животных в отсортированном порядке.
     * Поля для сортировки – хозяин (имя, по возрастанию),
     * если имена хозяев одинаковые - кличка животного.
     * Если и имя хозяина и кличка животного одинаковые -
     * раньше выводит животное, у которого больше вес.
     * @return список животных в отсортированном порядке.
     */
    public ArrayList<Card> sortedPetList(){
        return (ArrayList<Card>) values().stream()
                .sorted((c, d) -> {
                    int result = c.owner.name.compareToIgnoreCase(d.owner.name);
                    if (result == 0){
                        result = c.pet.nickname.compareToIgnoreCase(d.pet.nickname);
                        if (result == 0) {
                            result = Float.compare(c.pet.weight, d.pet.weight) * -1;
                        }
                    }
                    return result;
                })
                .collect(Collectors.toList());
    }

    /* Override Methods */

    @Override
    public String toString() {
        Iterator<Map.Entry<Long, Card>> iterator = entrySet().iterator();
        StringBuilder str = new StringBuilder("CardFile...\n");
        while (iterator.hasNext()) {
            str.append(iterator.next()).append("\n");
        }
        return str.toString();
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null || Card.class != value.getClass()) return false;
        for (Card val : values()) {
            if (val.equals(value)) {
                return true;
            }
        }
        return false;
    }

}
