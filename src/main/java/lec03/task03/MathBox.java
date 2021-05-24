package lec03.task03;

import java.util.HashSet;
import java.util.Objects;

public class MathBox<T extends Number> extends ObjectBox {

    /**
     * Конструктор
     * @param numbers массив {@code Number} для заполнения коллекции
     *
     * @see Number
     */
    public MathBox(Number[] numbers) {

        super(new HashSet<T>());

        if (numbers != null) {              // проверка на пустой массив

            for (Number num : numbers) {    // заполнение массива
                addObject(num);
            }

        }
    }

    /**
     * Переопределение метода для контроля добовляемых объектов.
     * При добавлении объекта класса {@code Object} генерируется исключение {@code Exception}.
     */
    @Override
    public boolean addObject(Object o) {
        if (o.getClass() != Object.class) {     // проверяем на принадлежность к классу Object
            return super.addObject(o);
        } else {                                // исключение при o == Object class
            Exception e = new Exception("You can't use the Object class with the addObject method of the MathBox class.");
            System.out.println(e.fillInStackTrace().toString());
        }
        return false;
    }

    /**
     * Метод выполняет поочередное деление всех хронящихся в объекте элементов на делитель {@code divider}.
    */
    public <V extends Number> void splitter(V divider) {
        Double tmpRlt;
        HashSet<T> tmpSet = new HashSet<>(this.objects.size(),0.01f);
        for (Object object : this.objects) {
            Number item = (Number) object;
            tmpRlt = item.doubleValue() / divider.doubleValue();
            tmpSet.add((T) tmpRlt);
        }
        this.objects = new HashSet<>(tmpSet);
    }

    /**
     * Возвращяет сумму всех элементов коллекции
     * @return сумма элементов
     */
    public double summator() {
        double sum = 0;
        for (Object object : this.objects) {
            Number item = (Number) object;
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
        return deleteObject(integer);
    }

    /**
     * Строковый вид содержимиго экземпляра
     * @return строка содержимого
     */
    @Override
    public String toString() {
        return "MathBox{" +
                "objects=" + objects +
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
        return Objects.equals(objects, mathBox.objects);
    }

    /**
     * Вычисляет хэш-код
     * @return значение хэш-кода
     */
    @Override
    public int hashCode() {
        return Objects.hash(objects);
    }
}
