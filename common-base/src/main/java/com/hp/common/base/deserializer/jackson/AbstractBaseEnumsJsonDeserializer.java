package com.hp.common.base.deserializer.jackson;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.hp.common.base.enums.BaseEnum;
import com.hp.common.base.enums.BaseEnumBasedAdapter;
import com.hp.common.base.enums.CodeEnum;
import com.hp.common.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * The deserializer only works when the C is the
 * concrete implementation of the Collection interface.
 *
 * @author hp
 */
@Slf4j
public abstract class AbstractBaseEnumsJsonDeserializer<T extends Enum<T> & BaseEnum<T, E>, E, C extends Collection<T>> extends JsonDeserializer<C> implements BaseEnumBasedAdapter<T, E> {

    private final Class<T> baseEnumType = Objects.requireNonNull(getBaseEnumType());
    private final Class<E> baseEnumCodeType = Objects.requireNonNull(getBaseEnumCodeType());
    private final Class<C> fieldType = Objects.requireNonNull(getFieldType());

    @SuppressWarnings("unchecked")
    protected Class<C> getFieldType() {
        final Type genericSuperclass = this.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType parameterizedType) {
            final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            return (Class<C>) ((ParameterizedType) actualTypeArguments[actualTypeArguments.length - 1]).getRawType();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public C deserialize(JsonParser p, DeserializationContext context) throws IOException {
        final C c = (C) CollUtil.create(fieldType, baseEnumType);
        JsonNode node = p.getCodec().readTree(p);
        if (node.isNull() || node.isEmpty()) {
            return c;
        }
        if (node.isArray()) {
            for (JsonNode elementNode : node) {
                final E e = p.getCodec().treeToValue(elementNode, baseEnumCodeType);
                final Optional<T> t = Optional.ofNullable(BaseEnum.parseByCode(baseEnumType, e));
                c.add(t.orElseThrow(() -> new BusinessException(CodeEnum.EnumConstantNotFound, e)));
            }
        }
        return c;
    }
}
