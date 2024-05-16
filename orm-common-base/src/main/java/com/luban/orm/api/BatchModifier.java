
package com.luban.orm.api;

import java.util.function.Consumer;

/**
 * @author hp
 */
public interface BatchModifier<AGGREGATE_ROOT> extends BatchHooks<AGGREGATE_ROOT>, BatchExecutor<AGGREGATE_ROOT>, OrmOperation {

    BatchModifier<AGGREGATE_ROOT> modify(Consumer<AGGREGATE_ROOT> consumer);
}
