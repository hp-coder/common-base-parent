package com.hp.common.base.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hp
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class BaseEnumBasedConditionalGenericConverter implements ConditionalGenericConverter {

    private final BaseEnumRegistrar baseEnumRegistrar;

    @Override
    public boolean matches(@NonNull TypeDescriptor sourceType, TypeDescriptor targetType) {
        return baseEnumRegistrar.contains(targetType.getType());
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return baseEnumRegistrar.getBaseEnumTypes().stream()
                .map(cls -> new ConvertiblePair(String.class, cls))
                .collect(Collectors.toSet());
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Object convert(Object source, @NonNull TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
        final String value = (String) source;
        final List<? extends BaseEnum> baseEnums = baseEnumRegistrar.get(targetType.getType());
        return baseEnums.stream()
                .filter(baseEnum -> Objects.equals(String.valueOf(baseEnum.getCode()), value))
                .findFirst()
                .orElse(null);
    }
}
