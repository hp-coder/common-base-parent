package com.hp.common.base.visitor;

/**
 * @author hp
 */
@FunctionalInterface
public interface Visitor<T> {

    void visit(T t);

    static <T> Visitor<T> defaultVisitor() {
        return t -> {};
    }
}
