package com.hp.sdk.annotations;

import java.lang.annotation.*;

/**
 * @author hp
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Documented
public @interface ApiMeta {

    String path();

    String apiName();

    boolean auth() default true;
}
