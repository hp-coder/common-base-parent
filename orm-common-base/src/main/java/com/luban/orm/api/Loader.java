package com.luban.orm.api;

import java.util.function.Supplier;

/**
 * @author hp
 */
public interface Loader<AGGREGATE_ROOT> extends OrmOperation {

    Modifier<AGGREGATE_ROOT> aggregation(Supplier<AGGREGATE_ROOT> supplier);

}
