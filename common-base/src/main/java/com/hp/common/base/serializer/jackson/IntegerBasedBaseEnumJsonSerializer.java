package com.hp.common.base.serializer.jackson;

import com.hp.common.base.enums.BaseEnum;

/**
 * @author hp
 */
public class IntegerBasedBaseEnumJsonSerializer<T extends Enum<T> & BaseEnum<T, Integer>> extends AbstractBaseEnumJsonSerializer<T, Integer> {
    public IntegerBasedBaseEnumJsonSerializer() {
    }

    public IntegerBasedBaseEnumJsonSerializer(String namingConventionFormat) {
        super(namingConventionFormat);
    }

    public IntegerBasedBaseEnumJsonSerializer(JsonSerializerWriter writer) {
        super(writer);
    }

    public IntegerBasedBaseEnumJsonSerializer(String namingConventionFormat, JsonSerializerWriter writer) {
        super(namingConventionFormat, writer);
    }
}
