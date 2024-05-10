package com.luban.orm.api;

import java.util.function.Supplier;

/**
 * @author hp
 */
public interface Creator<AGGREGATE_ROOT> extends OrmOperation {

    Modifier<AGGREGATE_ROOT> create(Supplier<AGGREGATE_ROOT> supplier);

}
