package com.luban.common.base.valueobject.ip;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.Preconditions;
import com.luban.common.base.exception.NullValueObjectException;
import com.luban.common.base.valueobject.AbstractStringBasedSingleValueObject;
import com.luban.common.base.valueobject.Patterns;

/**
 * @author hp
 */
public final class Ip extends AbstractStringBasedSingleValueObject {

    private Ip(String value) throws NullValueObjectException {
        super(value);
    }

    @JsonCreator
    public static Ip of(String value) {
        try {
            return new Ip(value);
        } catch (NullValueObjectException ignore) {
            return null;
        }
    }

    @Override
    public void validate(String value) throws IllegalArgumentException {
        Preconditions.checkArgument(Patterns.IP_PATTERN.asPredicate().test(value), "ip地址格式错误");
    }

}
