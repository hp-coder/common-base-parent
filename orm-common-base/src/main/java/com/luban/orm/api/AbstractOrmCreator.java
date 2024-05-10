package com.luban.orm.api;


import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * @author hp
 */
@Slf4j
public abstract class AbstractOrmCreator<AGGREGATION, REPOSITORY extends OrmRepository<AGGREGATION, ID>, ID extends Serializable>
        extends AbstractOrmOperator<AGGREGATION, REPOSITORY, ID>
        implements Loader<AGGREGATION> {

    public AbstractOrmCreator(REPOSITORY repository) {
        super(repository);
    }

    @Override
    public Modifier<AGGREGATION> aggregation(Supplier<AGGREGATION> supplier) {
        this.aggregation = supplier.get();
        return this;
    }

    @Override
    protected Supplier<AGGREGATION> doExecute() {
        return () -> {
            this.repository.save(this.aggregation);
            return this.aggregation;
        };
    }
}
