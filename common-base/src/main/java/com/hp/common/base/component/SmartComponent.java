package com.hp.common.base.component;

/**
 * @author hp
 */
public interface SmartComponent<T> {

    default String id() {
        return this.getClass().getName();
    }

    boolean support(T t);
}
