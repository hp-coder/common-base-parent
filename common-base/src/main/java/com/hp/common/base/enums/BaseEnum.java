package com.hp.common.base.enums;

import com.hp.common.base.model.Selectable;

import java.util.Objects;

public interface BaseEnum<T extends Enum<T> & BaseEnum<T, E>, E> extends Selectable {

    E getCode();

    String getName();

    static <T extends Enum<T> & BaseEnum<T, E>, E> T parseByCode(Class<T> cls, E code) {
        if (code == null) {
            return null;
        }
        for (T t : cls.getEnumConstants()) {
            if (Objects.equals(t.getCode(), code)) {
                return t;
            }
        }
        return null;
    }

    static <T extends Enum<T> & BaseEnum<T, E>, E> T parseByName(Class<T> cls, String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        for (T t : cls.getEnumConstants()) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }
}
