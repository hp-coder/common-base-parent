package com.hp.orm.api;


import cn.hutool.core.collection.CollUtil;
import com.hp.common.base.enums.CodeEnum;
import com.hp.common.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author hp
 */
@Slf4j
public abstract class AbstractOrmBatchUpdater<AGGREGATE_ROOT, REPOSITORY extends OrmRepository<AGGREGATE_ROOT, ID>, ID extends Serializable>
        extends AbstractOrmBatchOperator<AGGREGATE_ROOT, REPOSITORY, ID>
        implements BatchUpdaterLoader<AGGREGATE_ROOT, ID> {

    public AbstractOrmBatchUpdater(REPOSITORY repository) {
        super(repository);
        this.onSuccessDefault = entity -> log.debug("{} batch is successfully updated", entity.getClass().getName());
        this.onFailureDefault = e -> { throw new BusinessException(CodeEnum.UpdateError, e);};
    }

    @Override
    public BatchModifier<AGGREGATE_ROOT> aggregations(Supplier<Collection<AGGREGATE_ROOT>> supplier) {
        this.aggregateRoots = Objects.requireNonNull(supplier).get();
        return this;
    }

    @Override
    public BatchModifier<AGGREGATE_ROOT> aggregationsById(Collection<ID> ids) {
        this.aggregateRoots = this.repository.findAllById(ids);
        return this;
    }

    @Override
    protected Consumer<Collection<AGGREGATE_ROOT>> doExecute() {
        return aggregateRoots -> {
            if (CollUtil.isEmpty(aggregateRoots)) {
                return;
            }
            this.repository.updateAllById(aggregateRoots);
        };
    }
}
