package com.luban.orm.api;

import java.util.Optional;

/**
 * @author hp
 */
public interface Executor<AGGREGATE_ROOT> extends OrmOperation {

    Optional<AGGREGATE_ROOT> execute();

}
