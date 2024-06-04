package com.hp.orm.api;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.hp.common.base.enums.CodeEnum;
import com.hp.common.base.exception.BusinessException;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author hp
 */
@Slf4j
public abstract class AbstractOrmBatchOperator<AGGREGATE_ROOT, REPOSITORY extends OrmRepository<AGGREGATE_ROOT, ID>, ID extends Serializable> implements BatchModifier<AGGREGATE_ROOT>, BatchHooks<AGGREGATE_ROOT> {
    protected final REPOSITORY repository;
    protected Collection<AGGREGATE_ROOT> aggregateRoots;
    protected List<Consumer<Collection<AGGREGATE_ROOT>>> onSuccessConsumers = Lists.newArrayList();
    protected List<Consumer<? super Throwable>> onFailureConsumers = Lists.newArrayList();
    protected Consumer<Collection<AGGREGATE_ROOT>> onSuccessDefault = entity -> log.info("{} is successfully Saved", entity.getClass().getName());
    protected Consumer<? super Throwable> onFailureDefault = e -> {
        throw new BusinessException(CodeEnum.SaveError, e);
    };

    public AbstractOrmBatchOperator(REPOSITORY repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public BatchModifier<AGGREGATE_ROOT> modify(Consumer<AGGREGATE_ROOT> consumer) {
        Objects.requireNonNull(this.aggregateRoots).forEach(root -> Objects.requireNonNull(consumer).accept(root));
        return this;
    }

    @Override
    public BatchHooks<AGGREGATE_ROOT> registerOnSuccess(Consumer<Collection<AGGREGATE_ROOT>> consumer) {
        this.onSuccessConsumers.addLast(Objects.requireNonNull(consumer));
        return this;
    }

    @Override
    public BatchHooks<AGGREGATE_ROOT> registerOnFailure(Consumer<? super Throwable> consumer) {
        this.onFailureConsumers.addLast(Objects.requireNonNull(consumer));
        return this;
    }

    @Override
    public Collection<AGGREGATE_ROOT> execute() {
        return Try.of(() -> {
                    doExecute().accept(Objects.requireNonNull(this.aggregateRoots));
                    return Objects.requireNonNull(this.aggregateRoots);
                })
                .onSuccess(roots -> getOnSuccessConsumers().forEach(consumer -> consumer.accept(roots)))
                .onFailure(throwable -> getOnFailureConsumers().forEach(consumer -> consumer.accept(throwable)))
                .getOrNull();
    }

    protected List<Consumer<Collection<AGGREGATE_ROOT>>> getOnSuccessConsumers() {
        return CollUtil.defaultIfEmpty(this.onSuccessConsumers, Lists.newArrayList(this.onSuccessDefault));
    }

    protected List<Consumer<? super Throwable>> getOnFailureConsumers() {
        return CollUtil.defaultIfEmpty(this.onFailureConsumers, Lists.newArrayList(this.onFailureDefault));
    }

    protected abstract Consumer<Collection<AGGREGATE_ROOT>> doExecute();
}
