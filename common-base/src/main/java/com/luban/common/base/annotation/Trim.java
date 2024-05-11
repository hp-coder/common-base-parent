package com.luban.common.base.annotation;

import com.luban.common.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.*;

/**
 * Based on {@code cn.hutool.core.util.StrUtil.trim(value, mode)}
 * <p>
 *
 *
 * @author hp
 * @see com.luban.common.base.http.servlet.TrimRequestResponseBodyMethodProcessorDecorator
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Trim {

    TrimMode value() default TrimMode.TRIM_ALL;

    @Getter
    @AllArgsConstructor
    enum TrimMode implements BaseEnum<TrimMode, Integer> {
        /***/
        TRIM_END(1, "trimEnd"), TRIM_ALL(0, "trimAll"), TRIM_START(-1, "trimStart"),
        ;
        private final Integer code;
        private final String name;
    }
}
