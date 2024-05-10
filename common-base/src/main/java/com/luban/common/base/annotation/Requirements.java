
package com.luban.common.base.annotation;

import java.lang.annotation.*;

/**
 * @author HP 2023/2/13
 */
@Documented
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
public @interface Requirements {
    Requirement[] value();
}
