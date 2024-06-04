package com.hp.orm.api;

import java.util.Collection;

/**
 * @author hp
 */
public interface BatchExecutor<AGGREGATE_ROOT> extends OrmOperation {

    Collection<AGGREGATE_ROOT> execute();
}
