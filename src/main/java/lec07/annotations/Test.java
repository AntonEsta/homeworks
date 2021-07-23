package lec07.annotations;

import java.lang.annotation.*;

/**
 * Устанавливает признак тестирования класса
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Test {
    // количество повторов теста
    int testCount() default 1;
    // Наименование методов для сравнения между собой по времени выполнения
    String[] CompareMethodsByTimeRun() default "";
}