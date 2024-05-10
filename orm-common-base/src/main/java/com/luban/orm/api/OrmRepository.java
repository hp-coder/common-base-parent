package com.luban.orm.api;

import java.io.Serializable;

/**
 * @author hp
 */
public interface OrmRepository<AGGREGATION, ID extends Serializable> {

    void save(AGGREGATION aggregation);

    void updateById(AGGREGATION aggregation);

    AGGREGATION findById(ID id);
}
