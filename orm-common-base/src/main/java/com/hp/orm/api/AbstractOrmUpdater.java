package com.hp.orm.api;


import com.hp.common.base.enums.CodeEnum;
import com.hp.common.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author hp
 */
@Slf4j
public abstract class AbstractOrmUpdater<AGGREGATE_ROOT, REPOSITORY extends OrmRepository<AGGREGATE_ROOT, ID>, ID extends Serializable>
        extends AbstractOrmOperator<AGGREGATE_ROOT, REPOSITORY, ID>
        implements UpdaterLoader<AGGREGATE_ROOT, ID> {

    public AbstractOrmUpdater(REPOSITORY repository) {
        super(repository);
        this.onSuccessDefault = entity -> log.debug("{} is successfully updated", entity.getClass().getName());
        this.onFailureDefault = e -> { throw new BusinessException(CodeEnum.UpdateError, e);};
    }

    @Override
    public Modifier<AGGREGATE_ROOT> aggregation(Supplier<AGGREGATE_ROOT> supplier) {
        this.aggregateRoot = Optional.ofNullable(Objects.requireNonNull(supplier).get()).orElseThrow(() -> new BusinessException(CodeEnum.NotFindError));
        return this;
    }

    @Override
    public Modifier<AGGREGATE_ROOT> aggregationById(ID id) {
        this.aggregateRoot = this.repository.findById(id).orElseThrow(() -> new BusinessException(CodeEnum.NotFindError));
        return this;
    }

    @Override
    protected Consumer<AGGREGATE_ROOT> doExecute() {
        return this.repository::updateById;
    }
}
