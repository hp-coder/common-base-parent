package com.luban.common.base.valueobject;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.luban.common.base.exception.NullValueObjectException;

import java.util.Objects;

/**
 * 配合jackson方便一些
 * <p>
 * 最好不要提供getter, 但是为了日志妥协一下
 *
 * @author hp
 * @see JsonAutoDetect;
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC)
public abstract class AbstractSingleValueObject<TYPE> implements SingleValueObject<TYPE> {

    protected final TYPE value;

    @Override
    public TYPE value() {
        return value;
    }

    protected AbstractSingleValueObject(TYPE value) throws NullValueObjectException {
        if (Objects.isNull(value)) {
            throw new NullValueObjectException();
        }
        this.value = value;
    }

    protected abstract void validate(TYPE value) throws IllegalArgumentException;

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractSingleValueObject<?> that = (AbstractSingleValueObject<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
