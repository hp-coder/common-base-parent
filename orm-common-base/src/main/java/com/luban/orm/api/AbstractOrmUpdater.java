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
public abstract class AbstractOrmUpdater<AGGREGATION, REPOSITORY extends OrmRepository<AGGREGATION, ID>, ID extends Serializable>
        extends AbstractOrmOperator<AGGREGATION, REPOSITORY, ID>
        implements UpdaterLoader<AGGREGATION, ID> {

    public AbstractOrmUpdater(REPOSITORY repository) {
        super(repository);
    }

    @Override
    public Modifier<AGGREGATION> aggregation(Supplier<AGGREGATION> supplier) {
        this.aggregation = Optional.ofNullable(Objects.requireNonNull(supplier).get()).orElseThrow(() -> new BusinessException(CodeEnum.NotFindError));
        return this;
    }

    @Override
    public Modifier<AGGREGATION> aggregationById(ID id) {
        this.aggregation = Optional.ofNullable(repository.findById(id)).orElseThrow(() -> new BusinessException(CodeEnum.NotFindError));
        return this;
    }

    @Override
    protected Supplier<AGGREGATION> doExecute() {
        return () -> {
            this.repository.updateById(this.aggregation);
            return this.aggregation;
        };
    }
}
