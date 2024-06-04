package com.hp.common.base.valueobject.qq;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.Preconditions;
import com.hp.common.base.exception.NullValueObjectException;
import com.hp.common.base.valueobject.AbstractStringBasedSingleValueObject;
import com.hp.common.base.valueobject.Patterns;

/**
 * @author hp
 */
public final class Qq extends AbstractStringBasedSingleValueObject {

    private Qq(String value) throws NullValueObjectException {
        super(value);
    }

    @JsonCreator
    public static Qq of(String value){
        try {
            return new Qq(value);
        }catch (NullValueObjectException ignore){
            return null;
        }
    }

    @Override
    public void validate(String value) throws IllegalArgumentException {
        Preconditions.checkArgument(Patterns.QQ_PATTERN.asPredicate().test(value), "QQ号码格式错误");
    }
}
