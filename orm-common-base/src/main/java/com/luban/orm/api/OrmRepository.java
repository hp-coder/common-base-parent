package com.luban.orm.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * @author hp
 */
public interface OrmRepository<AGGREGATE_ROOT, ID extends Serializable> {

    void save(AGGREGATE_ROOT aggregateRoot);

    void saveAll(Collection<AGGREGATE_ROOT> aggregateRoots);

    void updateById(AGGREGATE_ROOT aggregateRoot);

    void updateAllById(Collection<AGGREGATE_ROOT> aggregateRoots);

    Optional<AGGREGATE_ROOT> findById(ID id);

    Collection<AGGREGATE_ROOT> findAllById(Collection<ID> ids);

}
