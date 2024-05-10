package com.luban.orm.api;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author hp
 */
public interface OrmRepository<AGGREGATE_ROOT, ID extends Serializable> {

    void save(AGGREGATE_ROOT aggregation);

    void updateById(AGGREGATE_ROOT aggregation);

    Optional<AGGREGATE_ROOT> findById(ID id);
}
