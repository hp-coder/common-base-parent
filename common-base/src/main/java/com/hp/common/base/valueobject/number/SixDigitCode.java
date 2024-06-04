package com.hp.common.base.valueobject.number;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.Preconditions;
import com.hp.common.base.exception.NullValueObjectException;
import com.hp.common.base.valueobject.AbstractStringBasedSingleValueObject;
import com.hp.common.base.valueobject.Patterns;

/**
 * @author hp
 */
public final class SixDigitCode extends AbstractStringBasedSingleValueObject {

    private SixDigitCode(String value) throws NullValueObjectException {
        super(value);
    }

    @JsonCreator
    public static SixDigitCode of(String value){
        try {
            return new SixDigitCode(value);
        }catch (NullValueObjectException ignore){
            return null;
        }
    }

    @Override
    public void validate(String value) throws IllegalArgumentException {
        Preconditions.checkArgument(Patterns.SIX_DIGIT_CODE_PATTERN.asPredicate().test(value), "验证码格式错误");
    }
}
