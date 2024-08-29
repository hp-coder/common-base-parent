package com.hp.common.base.valueobject;

import java.io.Serializable;

/**
 * When used in GET APIs, annotate parameters with {@code @RequestParam}.
 * <p>
 * Implementations must provide valid instantiating methods, such as:
 * <ul>
 *     <li>a static method named {@code to(value1,value2...)}</li>
 *     <li>any static factory methods named {@code valueOf(value1,value2...)}, {@code of(value1,value2...)}, or {@code from(value1,value2...)}</li>
 *     <li>any constructors that accept input values.</li>
 * </ul>
 *
 * @author hp
 */
public interface ValueObject extends Serializable {

}
