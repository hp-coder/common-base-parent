package com.luban.orm.api;

import java.util.Optional;

/**
 * @author hp
 * @date 2022/10/18
 */
public interface Executor<AGGREGATE_ROOT> extends OrmOperation {

    Optional<AGGREGATE_ROOT> execute();

}
