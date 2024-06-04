package com.hp.common.base.serializer.jackson;

import com.hp.common.base.enums.BaseEnum;

/**
 * @author hp
 */
public class StringBasedBaseEnumJsonSerializer<T extends Enum<T> & BaseEnum<T, String>> extends AbstractBaseEnumJsonSerializer<T, String> {
    public StringBasedBaseEnumJsonSerializer() {
    }

    public StringBasedBaseEnumJsonSerializer(String namingConventionFormat) {
        super(namingConventionFormat);
    }

    public StringBasedBaseEnumJsonSerializer(JsonSerializerWriter writer) {
        super(writer);
    }

    public StringBasedBaseEnumJsonSerializer(String namingConventionFormat, JsonSerializerWriter writer) {
        super(namingConventionFormat, writer);
    }
}
