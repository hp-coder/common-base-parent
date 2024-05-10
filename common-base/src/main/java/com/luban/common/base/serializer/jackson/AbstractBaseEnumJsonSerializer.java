package com.luban.common.base.serializer.jackson;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.base.Preconditions;
import com.luban.common.base.enums.BaseEnum;
import com.luban.common.base.enums.BaseEnumBasedAdapter;

import java.io.IOException;
import java.util.Objects;

/**
 * @author hp
 */
public abstract class AbstractBaseEnumJsonSerializer<T extends Enum<T> & BaseEnum<T, E>, E> extends JsonSerializer<T> implements BaseEnumBasedAdapter<T, E> {

    protected final String namingConventionFormat;

    protected final JsonSerializerWriter writer;

    public AbstractBaseEnumJsonSerializer() {
        this.namingConventionFormat = "%sName";
        this.writer = new JsonSerializerWriter.DefaultJsonSerializerWriter();
    }

    public AbstractBaseEnumJsonSerializer(String namingConventionFormat) {
        Preconditions.checkArgument(StrUtil.isNotEmpty(namingConventionFormat), "Naming convention format can not be empty");
        this.namingConventionFormat = namingConventionFormat;
        this.writer = new JsonSerializerWriter.DefaultJsonSerializerWriter();
    }

    public AbstractBaseEnumJsonSerializer(JsonSerializerWriter writer) {
        Preconditions.checkArgument(Objects.nonNull(writer), "JsonSerializerWriter can not be null");
        this.namingConventionFormat = "%sName";
        this.writer = writer;
    }

    public AbstractBaseEnumJsonSerializer(String namingConventionFormat, JsonSerializerWriter writer) {
        Preconditions.checkArgument(StrUtil.isNotEmpty(namingConventionFormat), "Naming convention format can not be empty");
        Preconditions.checkArgument(Objects.nonNull(writer), "JsonSerializerWriter can not be null");
        this.namingConventionFormat = namingConventionFormat;
        this.writer = writer;
    }

    protected String getBaseEnumField(JsonGenerator jsonGenerator) {
        return jsonGenerator.getOutputContext().getCurrentName();
    }

    protected String getBaseEnumNameField(JsonGenerator jsonGenerator) {
        return namingConventionFormat.formatted(Objects.requireNonNull(getBaseEnumField(jsonGenerator)));
    }

    @Override
    public void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.isNull(t)) {
            jsonGenerator.writeNull();
            jsonGenerator.writeNullField(getBaseEnumNameField(jsonGenerator));
        }
        writer.write(jsonGenerator, t.getCode());
        jsonGenerator.writeStringField(getBaseEnumNameField(jsonGenerator), t.getName());
    }
}
