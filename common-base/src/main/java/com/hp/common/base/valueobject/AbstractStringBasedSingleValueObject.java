package com.hp.common.base.valueobject;

import cn.hutool.core.util.StrUtil;
import com.hp.common.base.exception.NullValueObjectException;

/**
 * @author hp
 */
public abstract class AbstractStringBasedSingleValueObject extends AbstractSingleValueObject<String> {
    protected AbstractStringBasedSingleValueObject(String value) throws NullValueObjectException {
        super(value);
        if (StrUtil.isEmpty(value)) {
            throw new NullValueObjectException();
        }
        validate(value);
    }
}
