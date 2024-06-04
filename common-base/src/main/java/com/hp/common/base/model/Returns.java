package com.hp.common.base.model;

import cn.hutool.http.HttpStatus;
import com.hp.common.base.enums.BaseEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;

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
        return new Returns<>(baseEnum);
    }

    public static <T> Returns<T> success() {
        return Returns.of(DefaultCode.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    public static <T> Returns<T> success(T data) {
        return (Returns<T>) Returns.success().data(data);
    }

    public static <T> Returns<T> fail() {
        return Returns.of(DefaultCode.FAIL);
    }

    public static <T> Returns<T> fail(BaseEnum<? extends Enum<?>, Integer> codeEnum) {
        return new Returns<>(codeEnum);
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
        return !Objects.equals(DefaultCode.SUCCESS.getCode(), getCode());
    }


    /**
     * @author hp
     */
    @Getter
    @AllArgsConstructor
    private enum DefaultCode implements BaseEnum<DefaultCode, Integer> {
        /***/
        SUCCESS(HttpStatus.HTTP_OK, "操作成功"),
        FAIL(HttpStatus.HTTP_INTERNAL_ERROR, "操作失败"),
        ;
        private final Integer code;
        private final String name;

        public static Optional<DefaultCode> of(Integer code) {
            return Optional.ofNullable(BaseEnum.parseByCode(DefaultCode.class, code));
        }
    }
}
