package lec03.task01;

import java.util.HashSet;
import java.util.Objects;

public class MathBox<T extends Number> {

    HashSet<T> numbers = new HashSet<>();

    /**
     * Конструктор
     * @param numbers массив {@code Number} для заполнения коллекции
     *
     * @see Number
     */
    MathBox(Number[] numbers) {
        if (numbers == null) return;
        for (Number num : numbers) {
            this.numbers.add((T) num);
        }
    }

    /**
     * Метод выполняет поочередное деление всех хронящихся в объекте элементов на делитель {@code divider}.
     */
    public  <V extends Number> void splitter(V divider) {
        Double tmpRlt;
        HashSet<T> tmpSet = new HashSet<>(this.numbers.size(),0.01f);
        for (T item : this.numbers) {
            tmpRlt = item.doubleValue() / divider.doubleValue();
            tmpSet.add((T)tmpRlt);
        }
        this.numbers = new HashSet<>(tmpSet);
    }

    /**
     * Возвращяет сумму всех элементов коллекции
     * @return сумма элементов
     */
    public double summator() {
        double sum = 0;

        for (T item : this.numbers) {
            sum += item.doubleValue();
        }

        return sum;
    }

    /**
     * Удаляет указанное значение из коллекции
     * @param integer значение необходимое удалить
     * @return {@code true} при удалении элемента
     */
    public boolean deleteEqual(Integer integer){
        return this.numbers.remove(integer);
    }

    /**
     * Строковый вид содержимиго экземпляра
     * @return строка содержимого
     */
    @Override
    public String toString() {
        return "MathBox{" +
                "numbers=" + numbers +
                '}';
    }

    /**
     * Сравнение экземпляров
     * @param o экземпляр
     * @return {@code true} если экземпляры идентичные
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathBox<?> mathBox = (MathBox<?>) o;
        return Objects.equals(numbers, mathBox.numbers);
    }

    /**
     * Вычисляет хэш-код
     * @return значение хэш-кода
     */
    @Override
    public int hashCode() {
        return Objects.hash(numbers);
    }
}
