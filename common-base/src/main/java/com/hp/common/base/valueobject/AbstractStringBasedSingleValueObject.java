package com.hp.common.base.valueobject;

import cn.hutool.core.util.StrUtil;
import com.hp.common.base.exception.NullValueObjectException;

/**
 * @author hp
 */
public abstract class AbstractStringBasedSingleValueObject extends AbstractSingleValueObject<String> {
    protected AbstractStringBasedSingleValueObject(String value) throws NullValueObjectException {
        super(StrUtil.trim(value, 0));
        postConstruct();
    }

    private void postConstruct() throws NullValueObjectException {
        if (StrUtil.isEmpty(this.value)) {
            throw new NullValueObjectException();
        }
        validate(this.value);
    }
}
