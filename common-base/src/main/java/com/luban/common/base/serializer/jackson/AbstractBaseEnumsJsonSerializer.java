package com.luban.common.base.serializer.jackson;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.base.Preconditions;
import com.luban.common.base.enums.BaseEnum;
import com.luban.common.base.enums.BaseEnumBasedAdapter;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

/**
 * @author hp
 */
public abstract class AbstractBaseEnumsJsonSerializer<T extends Enum<T> & BaseEnum<T, E>, E, C extends Collection<T>> extends JsonSerializer<C> implements BaseEnumBasedAdapter<T, E> {

    protected final String namingConventionFormat;

    protected final JsonSerializerWriter writer;

    public AbstractBaseEnumsJsonSerializer() {
        this.namingConventionFormat = "%sNames";
        this.writer = new JsonSerializerWriter.DefaultJsonSerializerWriter();
    }

    public AbstractBaseEnumsJsonSerializer(String namingConventionFormat) {
        Preconditions.checkArgument(StrUtil.isNotEmpty(namingConventionFormat), "Naming convention format can not be empty");
        this.namingConventionFormat = namingConventionFormat;
        this.writer = new JsonSerializerWriter.DefaultJsonSerializerWriter();
    }

    public AbstractBaseEnumsJsonSerializer(JsonSerializerWriter writer) {
        Preconditions.checkArgument(Objects.nonNull(writer), "JsonSerializerWriter can not be null");
        this.namingConventionFormat = "%sNames";
        this.writer = writer;
    }

    public AbstractBaseEnumsJsonSerializer(String namingConventionFormat, JsonSerializerWriter writer) {
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
    public void serialize(C c, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.isNull(c)) {
            jsonGenerator.writeNull();
            jsonGenerator.writeNullField(getBaseEnumNameField(jsonGenerator));
        }
        if (CollUtil.isEmpty(c)) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeStartArray();
        c.stream()
                .map(BaseEnum::getCode)
                .forEach(code -> writer.write(jsonGenerator, code));
        jsonGenerator.writeEndArray();

        jsonGenerator.writeArrayFieldStart(getBaseEnumNameField(jsonGenerator));
        c.stream()
                .map(BaseEnum::getName)
                .forEach(name -> {
                    try {
                        jsonGenerator.writeString(name);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        jsonGenerator.writeEndArray();
    }


}
