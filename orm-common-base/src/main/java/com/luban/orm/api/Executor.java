package com.luban.orm.api;

import java.util.Optional;

/**
 * @author hp
 * @date 2022/10/18
 */
public interface Executor<AGGREGATION> extends OrmOperation {

    Optional<AGGREGATION> execute();

}
