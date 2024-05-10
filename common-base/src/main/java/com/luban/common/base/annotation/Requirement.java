package com.luban.common.base.annotation;

import java.lang.annotation.*;

/**
 * @author hp
 */
@Repeatable(Requirements.class)
@Retention(RetentionPolicy.CLASS)
@Target({
        ElementType.ANNOTATION_TYPE,
        ElementType.PACKAGE,
        ElementType.MODULE,
        ElementType.TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.LOCAL_VARIABLE,
        ElementType.PARAMETER,
})
public @interface Requirement {

    String value();

    String[] requiredBy() default {};

    String requiredAt() default "";
}
