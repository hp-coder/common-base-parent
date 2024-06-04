package com.hp.orm.api;


import com.hp.common.base.enums.CodeEnum;
import com.hp.common.base.exception.BusinessException;
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
        this.onSuccessDefault = entity -> log.debug("{} is successfully saved", entity.getClass().getName());
        this.onFailureDefault = e -> { throw new BusinessException(CodeEnum.SaveError, e);};
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
