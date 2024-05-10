package com.luban.orm.api;

import java.util.function.Supplier;

/**
 * @author hp
 */
public interface Loader<AGGREGATION> extends OrmOperation {

    Modifier<AGGREGATION> aggregation(Supplier<AGGREGATION> supplier);

}
