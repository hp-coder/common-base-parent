package com.luban.common.base.serializer.jackson;

import com.luban.common.base.enums.BaseEnum;

import java.util.Collection;

/**
 * @author hp
 */
public class IntegerBasedBaseEnumsJsonSerializer<T extends Enum<T> & BaseEnum<T, Integer>, C extends Collection<T>> extends AbstractBaseEnumsJsonSerializer<T, Integer, C> {
    public IntegerBasedBaseEnumsJsonSerializer() {
    }

    public IntegerBasedBaseEnumsJsonSerializer(String namingConventionFormat) {
        super(namingConventionFormat);
    }

    public IntegerBasedBaseEnumsJsonSerializer(JsonSerializerWriter writer) {
        super(writer);
    }

    public IntegerBasedBaseEnumsJsonSerializer(String namingConventionFormat, JsonSerializerWriter writer) {
        super(namingConventionFormat, writer);
    }
}
