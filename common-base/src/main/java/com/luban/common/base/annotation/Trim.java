package com.luban.common.base.annotation;

import com.luban.common.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Based on {@code cn.hutool.core.util.StrUtil.trim(value, mode)}
 *
 * Annotation discovering priority: Method > Parameter > Type > Field
 * Annotated type priority: Field > Type > Parameter > Method
 *
 * @author hp
 * @see com.luban.common.base.http.servlet.TrimRequestResponseBodyMethodProcessorDecorator
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,
                ElementType.PARAMETER,
                ElementType.TYPE,
                ElementType.FIELD
        })
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
    }
}
