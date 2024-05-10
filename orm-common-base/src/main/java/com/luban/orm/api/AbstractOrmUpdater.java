package com.luban.orm.api;


import com.luban.common.base.enums.CodeEnum;
import com.luban.common.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
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
    }

    @Override
    public Modifier<AGGREGATE_ROOT> aggregation(Supplier<AGGREGATE_ROOT> supplier) {
        this.aggregation = Optional.ofNullable(Objects.requireNonNull(supplier).get()).orElseThrow(() -> new BusinessException(CodeEnum.NotFindError));
        return this;
    }

    @Override
    public Modifier<AGGREGATE_ROOT> aggregationById(ID id) {
        this.aggregation = repository.findById(id).orElseThrow(() -> new BusinessException(CodeEnum.NotFindError));
        return this;
    }

    @Override
    protected Supplier<AGGREGATE_ROOT> doExecute() {
        return () -> {
            this.repository.updateById(this.aggregation);
            return this.aggregation;
        };
    }
}
