
package com.luban.orm.api;

import java.util.function.Consumer;

/**
 * @author hp
 * @date 2022/10/18
 */
public interface Modifier<AGGREGATION> extends Hooks<AGGREGATION>, Executor<AGGREGATION>, OrmOperation {

    Modifier<AGGREGATION> modify(Consumer<AGGREGATION> consumer);
}
