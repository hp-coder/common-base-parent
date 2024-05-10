
package com.luban.common.base.annotation;

import java.lang.annotation.*;

/**
 * @author HP 2023/2/13
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodDesc {
    String value();
}
