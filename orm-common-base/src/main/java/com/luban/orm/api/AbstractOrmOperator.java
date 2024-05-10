package com.luban.orm.api;

import com.luban.common.base.enums.CodeEnum;
import com.luban.common.base.exception.BusinessException;
import io.vavr.control.Try;
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
public abstract class AbstractOrmOperator<AGGREGATE_ROOT, REPOSITORY extends OrmRepository<AGGREGATE_ROOT, ID>, ID extends Serializable> implements Modifier<AGGREGATE_ROOT>, Hooks<AGGREGATE_ROOT> {
    protected final REPOSITORY repository;

    protected AGGREGATE_ROOT aggregation;

    protected Consumer<AGGREGATE_ROOT> onSuccess = entity -> log.info("Successfully Saved");

    protected Consumer<? super Throwable> onFailure = e -> {
        throw new BusinessException(CodeEnum.SaveError, e);
    };

    public AbstractOrmOperator(REPOSITORY repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Modifier<AGGREGATE_ROOT> modify(Consumer<AGGREGATE_ROOT> consumer) {
        Objects.requireNonNull(consumer).accept(Objects.requireNonNull(this.aggregation));
        return this;
    }

    @Override
    public Hooks<AGGREGATE_ROOT> onSuccess(Consumer<AGGREGATE_ROOT> consumer) {
        this.onSuccess = Objects.requireNonNull(consumer);
        return this;
    }

    @Override
    public Hooks<AGGREGATE_ROOT> onFailure(Consumer<? super Throwable> consumer) {
        this.onFailure = Objects.requireNonNull(consumer);
        return this;
    }

    @Override
    public Optional<AGGREGATE_ROOT> execute() {
        return Optional.ofNullable(Try.of(() -> Objects.requireNonNull(doExecute()).get())
                .onSuccess(this.onSuccess)
                .onFailure(this.onFailure)
                .getOrNull());
    }

    protected abstract Supplier<AGGREGATE_ROOT> doExecute();
}
