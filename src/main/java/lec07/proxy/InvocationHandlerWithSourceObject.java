package lec07.proxy;

import lombok.Getter;

import java.lang.reflect.InvocationHandler;


/**
 * Abstract class implements {@link InvocationHandler} with source object
 */
public abstract class InvocationHandlerWithSourceObject implements InvocationHandler {

    @Getter
    private final Object sourceObject;

    public InvocationHandlerWithSourceObject(Object sourceObject) {
        this.sourceObject = sourceObject;
    }

}
