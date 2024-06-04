package com.hp.common.base.valueobject.contact;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.Preconditions;
import com.hp.common.base.exception.NullValueObjectException;
import com.hp.common.base.valueobject.AbstractStringBasedSingleValueObject;
import com.hp.common.base.valueobject.Patterns;

import java.util.Optional;

/**
 * @author hp
 */
public final class PhoneNumber extends AbstractStringBasedSingleValueObject {

    private PhoneNumber(String phoneNumber) throws NullValueObjectException {
        super(phoneNumber);
    }

    @JsonCreator
    public static PhoneNumber of(String value) {
        try {
            return new PhoneNumber(value);
        } catch (NullValueObjectException ignore) {
            return null;
        }
    }

    @JsonCreator
    public static PhoneNumber of(Long value) {
        return Optional.ofNullable(value)
                .map(String::valueOf)
                .map(PhoneNumber::of)
                .orElse(null);
    }

    @Override
    public void validate(String value) throws IllegalArgumentException {
        Preconditions.checkArgument(Patterns.PHONE_PATTERN.asPredicate().test(value), "手机号码格式错误");
    }
}
