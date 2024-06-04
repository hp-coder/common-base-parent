package com.hp.orm.api;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author hp
 */
public interface BatchLoader<AGGREGATE_ROOT> extends OrmOperation {

    BatchModifier<AGGREGATE_ROOT> aggregations(Supplier<Collection<AGGREGATE_ROOT>> supplier);

}
