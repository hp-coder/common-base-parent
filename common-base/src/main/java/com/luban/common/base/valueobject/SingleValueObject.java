package com.luban.common.base.valueobject;


import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author hp
 */
public interface SingleValueObject<TYPE> extends ValueObject {

    @JsonValue
    TYPE value();
}
