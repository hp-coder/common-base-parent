package com.hp.common.base.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author hp
 */
@FunctionalInterface
public interface TerFunction<T, U, E, R> {

    R apply(T t, U u, E e);

    default <V> TerFunction<T, U, E, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t, U u, E e) -> after.apply(apply(t, u, e));
    }

}
