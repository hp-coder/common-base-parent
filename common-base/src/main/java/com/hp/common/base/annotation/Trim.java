package com.hp.common.base.annotation;

import com.hp.common.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.*;

/**
 * Based on {@code cn.hutool.core.util.StrUtil.trim(value, mode)}
 * <p>
 * Usage:
 * <p>
 * In order to activate the processing, add the annotation on parameters
 * that were also annotated with {@code @RequestBody}. This will trigger
 * recursive checking of the fields in the parameters.
 * <p>
 * To trim specific fields, annotate on that field.
 * <p>
 * If the field is not a String type, the fields to be trimmed in the
 * object field also have to be annotated with {@code @Trim}.
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
 * @see com.hp.common.base.http.servlet.TrimRequestResponseBodyMethodProcessorDecorator
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Trim {

    Mode value() default Mode.ALL;

    @Getter
    @AllArgsConstructor
    enum Mode implements BaseEnum<Mode, Integer> {
        /***/
        END(1, "trimEnd"), ALL(0, "trimAll"), START(-1, "trimStart"),
        ;
        private final Integer code;
        private final String name;
    }
}
