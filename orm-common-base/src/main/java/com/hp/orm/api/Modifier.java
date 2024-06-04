
package com.hp.orm.api;

import java.util.function.Consumer;

/**
 * @author hp
 */
public interface Modifier<AGGREGATE_ROOT> extends Hooks<AGGREGATE_ROOT>, Executor<AGGREGATE_ROOT>, OrmOperation {

    Modifier<AGGREGATE_ROOT> modify(Consumer<AGGREGATE_ROOT> consumer);
}
