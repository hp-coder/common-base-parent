package com.luban.orm.api;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.luban.common.base.enums.CodeEnum;
import com.luban.common.base.exception.BusinessException;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author hp
 */
@Slf4j
public abstract class AbstractOrmOperator<AGGREGATE_ROOT, REPOSITORY
        extends OrmRepository<AGGREGATE_ROOT, ID>, ID extends Serializable>
        implements Modifier<AGGREGATE_ROOT>, Hooks<AGGREGATE_ROOT> {
    protected final REPOSITORY repository;
    protected AGGREGATE_ROOT aggregateRoot;
    protected List<Consumer<AGGREGATE_ROOT>> onSuccessConsumers = Lists.newArrayList();
    protected List<Consumer<? super Throwable>> onFailureConsumers = Lists.newArrayList();
    protected Consumer<AGGREGATE_ROOT> onSuccessDefault = entity -> log.debug("{} is successfully Saved", entity.getClass().getName());
    protected Consumer<? super Throwable> onFailureDefault = e -> {
        throw new BusinessException(CodeEnum.SaveError, e);
    };

    public AbstractOrmOperator(REPOSITORY repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Modifier<AGGREGATE_ROOT> modify(Consumer<AGGREGATE_ROOT> consumer) {
        Objects.requireNonNull(consumer).accept(Objects.requireNonNull(this.aggregateRoot));
        return this;
    }

    @Override
    public Hooks<AGGREGATE_ROOT> registerOnSuccess(Consumer<AGGREGATE_ROOT> consumer) {
        this.onSuccessConsumers.addLast(Objects.requireNonNull(consumer));
        return this;
    }

    @Override
    public Hooks<AGGREGATE_ROOT> registerOnFailure(Consumer<? super Throwable> consumer) {
        this.onFailureConsumers.addLast(Objects.requireNonNull(consumer));
        return this;
    }

    @Override
    public Optional<AGGREGATE_ROOT> execute() {
        return Optional.ofNullable(
                Try.of(() -> {
                            doExecute().accept(Objects.requireNonNull(this.aggregateRoot));
                            return Objects.requireNonNull(this.aggregateRoot);
                        })
                        .onSuccess(root -> getOnSuccessConsumers().forEach(consumer -> consumer.accept(root)))
                        .onFailure(throwable -> getOnFailureConsumers().forEach(consumer -> consumer.accept(throwable)))
                        .getOrNull()
        );
    }

    protected List<Consumer<AGGREGATE_ROOT>> getOnSuccessConsumers() {
        return CollUtil.defaultIfEmpty(this.onSuccessConsumers, Lists.newArrayList(this.onSuccessDefault));
    }

    public List<Consumer<? super Throwable>> getOnFailureConsumers() {
        return CollUtil.defaultIfEmpty(this.onFailureConsumers, Lists.newArrayList(this.onFailureDefault));
    }

    protected abstract Consumer<AGGREGATE_ROOT> doExecute();
}
