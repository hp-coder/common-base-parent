package com.luban.orm.api;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author hp
 */
public interface OrmRepository<AGGREGATION, ID extends Serializable> {

    void save(AGGREGATION aggregation);

    void updateById(AGGREGATION aggregation);

    Optional<AGGREGATION> findById(ID id);
}
