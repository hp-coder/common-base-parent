package com.luban.common.base.serializer.jackson;

import com.luban.common.base.enums.BaseEnum;

import java.util.Collection;

/**
 * @author hp
 */
public class StringBasedBaseEnumsJsonSerializer<T extends Enum<T> & BaseEnum<T, String>, C extends Collection<T>> extends AbstractBaseEnumsJsonSerializer<T, String, C> {
    public StringBasedBaseEnumsJsonSerializer() {
    }

    public StringBasedBaseEnumsJsonSerializer(String namingConventionFormat) {
        super(namingConventionFormat);
    }

    public StringBasedBaseEnumsJsonSerializer(JsonSerializerWriter writer) {
        super(writer);
    }

    public StringBasedBaseEnumsJsonSerializer(String namingConventionFormat, JsonSerializerWriter writer) {
        super(namingConventionFormat, writer);
    }
}
