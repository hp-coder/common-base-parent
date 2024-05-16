package com.luban.orm.api;


import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author hp
 */
@Slf4j
public abstract class AbstractOrmCreator<AGGREGATE_ROOT, REPOSITORY extends OrmRepository<AGGREGATE_ROOT, ID>, ID extends Serializable>
        extends AbstractOrmOperator<AGGREGATE_ROOT, REPOSITORY, ID>
        implements Loader<AGGREGATE_ROOT> {

    public AbstractOrmCreator(REPOSITORY repository) {
        super(repository);
    }

    @Override
    public Modifier<AGGREGATE_ROOT> aggregation(Supplier<AGGREGATE_ROOT> supplier) {
        this.aggregateRoot = supplier.get();
        return this;
    }

    @Override
    protected Consumer<AGGREGATE_ROOT> doExecute() {
        return this.repository::save;
    }
}
