package com.luban.common.base.enums;

import com.luban.common.base.exception.BusinessException;
import jakarta.annotation.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

/**
 * @author hp
 */
public interface BaseEnumBasedAdapter<T extends Enum<T> & BaseEnum<T, E>, E> {

    @SuppressWarnings("unchecked")
    default Class<T> getBaseEnumType() {
        final Class<? extends BaseEnumBasedAdapter<T, E>> aClass = (Class<? extends BaseEnumBasedAdapter<T, E>>) this.getClass();
        return Optional.ofNullable(getBaseEnumType(aClass)).orElseThrow(()-> new BusinessException(CodeEnum.SystemError));
    }

    @SuppressWarnings("unchecked")
    default Class<E> getBaseEnumCodeType() {
        final Class<? extends BaseEnumBasedAdapter<T, E>> aClass = (Class<? extends BaseEnumBasedAdapter<T, E>>) this.getClass();
        return Optional.ofNullable(getBaseEnumCodeType(aClass)).orElseThrow(()-> new BusinessException(CodeEnum.SystemError));
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private Class<E> getBaseEnumCodeType(Class<? extends BaseEnumBasedAdapter<T, E>> clazz){
        final Class<T> baseEnumType = getBaseEnumType(clazz);
        if (Objects.isNull(baseEnumType)){
            return null;
        }
        final Type[] genericInterfaces = baseEnumType.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType p) {
                final Type[] actualTypeArguments = p.getActualTypeArguments();
                return (Class<E>) actualTypeArguments[1];
            }
        }
        throw new RuntimeException("Can not find any generic types.");
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private Class<T> getBaseEnumType(Class<? extends BaseEnumBasedAdapter<T, E>> clazz) {
        if (Objects.isNull(clazz)) {
            return null;
        }
        for (Type genericInterface : clazz.getGenericInterfaces()) {
            final Class<T> type = getBaseEnumType(genericInterface);
            if (type != null) return type;
        }

        final Type genericSuperclass = clazz.getGenericSuperclass();
        final Class<T> type = getBaseEnumType(genericSuperclass);
        if (type != null) return type;

        return getBaseEnumType((Class<? extends BaseEnumBasedAdapter<T, E>>) genericSuperclass);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private Class<T> getBaseEnumType(Type type) {
        if (type instanceof ParameterizedType parameterizedType) {
            for (Type actualTypeArgument : parameterizedType.getActualTypeArguments()) {
                if (BaseEnum.class.isAssignableFrom((Class<?>) actualTypeArgument)) {
                    return (Class<T>) actualTypeArgument;
                }
            }
        }
        return null;
    }
}
