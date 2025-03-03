package com.hp.common.base.model;

import com.hp.common.base.enums.BaseEnum;
import com.hp.common.base.enums.CodeEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author hp
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class Returns<T> {

    private Integer code;

    private String message;

    private T data;

    public static <T> Returns<T> of(BaseEnum<?, Integer> baseEnum) {
        return Returns.of(baseEnum, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> Returns<T> of(BaseEnum<?, Integer> baseEnum, T data) {
        final Returns<Object> objectReturns = new Returns<>(baseEnum);
        return ((Returns<T>) objectReturns.data(data));
    }

    public static <T> Returns<T> success() {
        return Returns.of(CodeEnum.Success);
    }

    public static <T> Returns<T> success(T data) {
        return Returns.of(CodeEnum.Success, data);
    }

    public static <T> Returns<T> success(BaseEnum<? extends Enum<?>, Integer> code) {
        return Returns.of(code, null);
    }

    public static <T> Returns<T> success(BaseEnum<? extends Enum<?>, Integer> code, T data) {
        return Returns.of(code, data);
    }

    public static <T> Returns<T> fail() {
        return Returns.of(CodeEnum.Fail, null);
    }

    public static <T> Returns<T> fail(T data) {
        return Returns.of(CodeEnum.Fail, data);
    }

    public static <T> Returns<T> fail(BaseEnum<? extends Enum<?>, Integer> code) {
        return Returns.of(code, null);
    }

    public static <T> Returns<T> fail(BaseEnum<? extends Enum<?>, Integer> code, T data) {
        return Returns.of(code, data);
    }

    protected Returns() {
    }

    public Returns(BaseEnum<? extends Enum<?>, Integer> baseEnum) {
        this.code = baseEnum.getCode();
        this.message = baseEnum.getName();
    }

    public Returns<T> code(int code) {
        setCode(code);
        return this;
    }

    @SuppressWarnings("unchecked")
    public Returns<T> data(T data) {
        if (Objects.isNull(data)) {
            return this;
        }
        if (data instanceof Long || data.getClass().isAssignableFrom(Long.class)) {
            setData((T) String.valueOf(data));
        } else {
            setData(data);
        }
        return this;
    }

    public Returns<T> message(String message) {
        if (Objects.isNull(message)) {
            return this;
        }
        setMessage(message);
        return this;
    }

    public boolean succeed() {
        return !failed();
    }

    public boolean failed() {
        return !Objects.equals(CodeEnum.Success.getCode(), getCode());
    }
}
