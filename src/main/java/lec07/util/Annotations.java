package lec07.util;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Class of methods for the work with annotations.
 */
public final class Annotations {

    /**
     * Checks if the object's class has the annotation.
     * @param obj object to check
     * @param annotation indicates of the annotation class which must be checked
     * @return true if annotation is present
     */
    public static <T> boolean isAnnotated(@NonNull T obj, @NonNull Class<? extends Annotation> annotation) {
        return getAnnotation(obj, annotation) != null;
    }

    /**
     * Checks if the object's class has the annotation at methods, fields and etc.
     * @param obj object to check
     * @param annotation indicates of the annotation class which must be checked
     * @return true if annotation is present
     */
    public static <T> boolean isContainsAnnotation(@NonNull T obj, @NonNull Class<? extends Annotation> annotation) {
        return isAnnotated(obj, annotation) || isAnyMethodAnnotated(obj, annotation);
    }


    /**
     * Checks if the object's class has the annotation at methods, fields and etc.
     * @param obj object to check
     * @param annotation indicates of the annotation class which must be checked
     * @return true if annotation is present
     */
    @SafeVarargs
    public static <T> boolean isContainsAnyAnnotation(@NonNull T obj, @NonNull Class<? extends Annotation>... annotation) {
        for (Class<? extends Annotation> a : annotation) {
           return (isAnnotated(obj, a) || isAnyMethodAnnotated(obj, a))  ;
        }
        return false;
    }

    /**
     * Checks if the object's class has the annotation at any method.
     * @param obj object where is must will found the method
     * @param method method to check
     * @param annotation indicates of the annotation class which must be checked
     * @return {@code true} if methods consists of the annotation
     */
    @SneakyThrows
    public static <T> boolean isAnnotatedMethod(@NonNull T obj, @NonNull Method method, @NonNull Class<? extends Annotation> annotation) {
        Method clMethod = searchMethod(obj, method);
        return isAnnotated(clMethod, annotation);
    }

    /**
     * Checks if the object's class has the annotation at any method.
     * @param obj object to check
     * @param annotation indicates of the annotation class which must be checked
     * @return true if annotation is present
     */
    public static <T> boolean isAnyMethodAnnotated(@NonNull T obj, @NonNull Class<? extends Annotation> annotation) {
        Method[] methods = obj.getClass().getMethods();
        return Arrays.stream(methods).anyMatch((method)-> method.isAnnotationPresent(annotation));
    }

    /**
     * Gets implement of the annotation at the object.
     * @param obj object to check
     * @param annotation indicates of the annotation class which must be checked
     * @param <A> class extends {@link Annotation}
     * @return implement of {@code annotation}
     * @throws NullPointerException if the given annotation class is null
     */
    @SuppressWarnings("unchecked")
    public static <T, A extends Annotation> A getAnnotation(@NonNull T obj, @NonNull Class<? extends  Annotation> annotation) throws NullPointerException{
        if (obj instanceof GenericDeclaration) {
            Method method = (Method) obj;
            return (A) method.getDeclaredAnnotation(annotation);
        }
        return (A) obj.getClass().getDeclaredAnnotation(annotation);
    }

    /**
     * Finds the method at the class implement.
     * @param obj the object to check
     * @param method the method for search
     * @return the found method
     * @throws NoSuchMethodException thrown when a method cannot be found.
     */
    public static Method searchMethod(@NonNull Object obj, @NonNull Method method) throws NoSuchMethodException {
        Method clMethod;
        try {
            clMethod = getMethod(obj, method);
        } catch (NoSuchMethodException e) {
            clMethod = getDeclaredMethod(obj, method);
        }
        return clMethod;
    }

    /**
     * Returns a Method object that reflects the specified public member method of the class or interface represented by this Class object.
     * @param obj source object
     * @param method the method for search
     * @return the {@link Method} object
     * @throws NoSuchMethodException thrown when a particular method cannot be found.
     */
    private static Method getMethod(@NonNull Object obj, @NonNull Method method) throws NoSuchMethodException {
        return obj.getClass().getMethod(method.getName(), method.getParameterTypes());
    }

    /**
     * Returns a Method object that reflects the specified declared method of the class or interface represented by this Class object.
     * @param obj source object
     * @param method the method for search
     * @return the {@link Method} object
     * @throws NoSuchMethodException thrown when a particular method cannot be found.
     */
    private static Method getDeclaredMethod(@NonNull Object obj, @NonNull Method method) throws NoSuchMethodException {
        return obj.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
    }

}