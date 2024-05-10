package com.luban.orm.api;

import java.util.function.Supplier;

/**
 * @author hp
 */
public interface Creator<AGGREGATION> extends OrmOperation {

    Modifier<AGGREGATION> create(Supplier<AGGREGATION> supplier);

}
