package com.hp.common.base.valueobject.areacode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.Preconditions;
import com.hp.common.base.exception.NullValueObjectException;
import com.hp.common.base.valueobject.AbstractStringBasedSingleValueObject;
import com.hp.common.base.valueobject.Patterns;
import lombok.EqualsAndHashCode;

/**
 * @author hp
 */
@EqualsAndHashCode(callSuper = true)
public class AreaCode extends AbstractStringBasedSingleValueObject {

    protected AreaCode(String areaCode) throws NullValueObjectException {
        super(areaCode);
    }

    @JsonCreator
    public static AreaCode of(String value) {
        try {
            return new AreaCode(value);
        } catch (NullValueObjectException ignore) {
            return null;
        }
    }

    @Override
    public void validate(String value) throws IllegalArgumentException {
        Preconditions.checkArgument(Patterns.AREA_CODE_PATTERN.asPredicate().test(value), "地区编码格式错误");
    }
}
