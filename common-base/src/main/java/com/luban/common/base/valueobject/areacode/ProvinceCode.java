package com.luban.common.base.valueobject.areacode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.Preconditions;
import com.luban.common.base.exception.NullValueObjectException;
import com.luban.common.base.valueobject.Patterns;
import lombok.EqualsAndHashCode;

/**
 * @author hp
 */
@EqualsAndHashCode(callSuper = true)
public final class ProvinceCode extends AreaCode {
    private ProvinceCode(String areaCode) throws NullValueObjectException {
        super(areaCode);
    }

    @JsonCreator
    public static ProvinceCode of(String value) {
        try {
            return new ProvinceCode(value);
        } catch (NullValueObjectException e) {
            return null;
        }
    }

    @Override
    public void validate(String value) throws IllegalArgumentException {
        Preconditions.checkArgument(Patterns.PROVINCE_CODE_PATTER.asPredicate().test(value), "省级地区编码格式错误");
    }
}
