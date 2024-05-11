package com.luban.common.base.annotation;

import com.luban.common.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.*;

/**
 * Based on {@code cn.hutool.core.util.StrUtil.trim(value, mode)}
 * <p>
 * Usage:
 * <p>
 * Add this annotation on parameters that were also annotated with {@code @RequestBody} to activate the processing,
 * and the fields in the parameters will be checked recursively.
 * <p>
 * To trim fields, annotated on the specific field.
 * <p>
 * If the field is not a String type, the fields to be trimmed in the object field also has to be annotated
 * with {@code @Trim}.
 *
 *
 * <pre>
 * public MODIFIER method({@code @RequestBody @Trim }ParamClass){}
 *
 * class ParamClass {
 *      {@code @Trim}
 *      private String trimmingField;
 *
 *      {@code @Trim}
 *      private NoneStringField nonStringField;
 * }
 *
 * class NoneStringField {
 *      {@code @Trim}
 *      private String nonStringTrimmingField;
 * }
 * </pre>
 *
 * @author hp
 * @see com.luban.common.base.http.servlet.TrimRequestResponseBodyMethodProcessorDecorator
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Trim {

    TrimMode value() default TrimMode.ALL;

    @Getter
    @AllArgsConstructor
    enum TrimMode implements BaseEnum<TrimMode, Integer> {
        /***/
        END(1, "trimEnd"), ALL(0, "trimAll"), START(-1, "trimStart"),
        ;
        private final Integer code;
        private final String name;
    }
}
