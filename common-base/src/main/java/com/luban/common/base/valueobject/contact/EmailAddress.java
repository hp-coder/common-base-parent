package com.luban.common.base.valueobject.contact;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.Preconditions;
import com.luban.common.base.exception.NullValueObjectException;
import com.luban.common.base.valueobject.AbstractStringBasedSingleValueObject;
import com.luban.common.base.valueobject.Patterns;

/**
 * @author hp
 */
public final class EmailAddress extends AbstractStringBasedSingleValueObject {

    private EmailAddress(String value) throws NullValueObjectException {
        super(value);
    }

    @JsonCreator
    public static EmailAddress of(String value) {
        try {
            return new EmailAddress(value);
        } catch (NullValueObjectException ignore) {
            return null;
        }
    }

    @Override
    public void validate(String value) throws IllegalArgumentException {
        Preconditions.checkArgument(Patterns.EMAIL_PATTERN.asPredicate().test(value), "电子邮箱格式错误");
    }
}
