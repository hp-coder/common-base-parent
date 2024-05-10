
package com.luban.common.base.annotation;

import java.lang.annotation.*;

/**
 * @author HP 2023/2/13
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.LOCAL_VARIABLE)
public @interface VariableDesc {
    String value();
}
