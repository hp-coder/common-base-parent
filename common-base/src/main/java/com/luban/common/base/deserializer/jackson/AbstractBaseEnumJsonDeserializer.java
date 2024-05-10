package com.luban.common.base.deserializer.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.luban.common.base.enums.BaseEnum;
import com.luban.common.base.enums.BaseEnumBasedAdapter;
import com.luban.common.base.enums.CodeEnum;
import com.luban.common.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author hp
 */
@Slf4j
public abstract class AbstractBaseEnumJsonDeserializer<T extends Enum<T> & BaseEnum<T, E>, E> extends JsonDeserializer<T> implements BaseEnumBasedAdapter<T, E> {

    private final Class<E> baseEnumCodeType = Objects.requireNonNull(getBaseEnumCodeType());
    private final Class<T> baseEnumType = Objects.requireNonNull(getBaseEnumType());

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        final E code = p.readValueAs(baseEnumCodeType);
        if (Objects.isNull(code)) {
            return null;
        }
        return Optional.ofNullable(BaseEnum.parseByCode(baseEnumType, code))
                .orElseThrow(() -> new BusinessException(CodeEnum.EnumConstantNotFound, code));
    }
}
