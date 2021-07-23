package lec07.annotations;

import java.lang.annotation.*;

/**
 * Признак тестирования метода на время выполнения
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TimeMeasure {
}