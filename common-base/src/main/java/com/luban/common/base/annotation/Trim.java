package com.luban.common.base.annotation;

import com.luban.common.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author hp
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.FIELD})
public @interface Trim {

    TrimMode value() default TrimMode.TRIM_ALL;

    @Getter
    @AllArgsConstructor
    enum TrimMode implements BaseEnum<TrimMode, Integer> {
        /***/
        TRIM_END(1, "trimEnd"),
        TRIM_ALL(0, "trimAll"),
        TRIM_START(-1, "trimStart"),
        ;
        private final Integer code;
        private final String name;

        public static Optional<TrimMode> of(Integer code) {
            return Optional.ofNullable(BaseEnum.parseByCode(TrimMode.class, code));
        }

        public static Optional<TrimMode> ofName(String name) {
            return Arrays.stream(values())
                    .filter(i -> Objects.equals(name, i.getName()))
                    .findFirst();
        }
    }
}
