package com.hp.common.base.valueobject.socialcreditcode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.Preconditions;
import com.hp.common.base.exception.NullValueObjectException;
import com.hp.common.base.valueobject.AbstractStringBasedSingleValueObject;
import com.hp.common.base.valueobject.Patterns;

/**
 * @author hp
 */
public final class SocialCreditCode extends AbstractStringBasedSingleValueObject {

    private SocialCreditCode(String value) throws NullValueObjectException {
        super(value);
    }

    @JsonCreator
    public static SocialCreditCode of(String value){
        try {
            return new SocialCreditCode(value);
        }catch (NullValueObjectException ignore){
            return null;
        }
    }

    @Override
    public void validate(String value) throws IllegalArgumentException {
        Preconditions.checkArgument(Patterns.CREDIT_CODE_PATTERN.asPredicate().test(value), "统一社会信用代码格式错误");
    }
}
