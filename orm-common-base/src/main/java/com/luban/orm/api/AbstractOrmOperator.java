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
public abstract class AbstractOrmOperator<AGGREGATION, REPOSITORY extends OrmRepository<AGGREGATION, ID>, ID extends Serializable> implements Modifier<AGGREGATION>, Hooks<AGGREGATION> {
    protected final REPOSITORY repository;

    protected AGGREGATION aggregation;

    protected Consumer<AGGREGATION> onSuccess = entity -> log.info("Successfully Saved");

    protected Consumer<? super Throwable> onFailure = e -> {
        throw new BusinessException(CodeEnum.SaveError, e);
    };

    public AbstractOrmOperator(REPOSITORY repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Modifier<AGGREGATION> modify(Consumer<AGGREGATION> consumer) {
        Objects.requireNonNull(consumer).accept(Objects.requireNonNull(this.aggregation));
        return this;
    }

    @Override
    public Hooks<AGGREGATION> onSuccess(Consumer<AGGREGATION> consumer) {
        this.onSuccess = Objects.requireNonNull(consumer);
        return this;
    }

    @Override
    public Hooks<AGGREGATION> onFailure(Consumer<? super Throwable> consumer) {
        this.onFailure = Objects.requireNonNull(consumer);
        return this;
    }

    @Override
    public Optional<AGGREGATION> execute() {
        return Optional.ofNullable(Try.of(() -> Objects.requireNonNull(doExecute()).get())
                .onSuccess(this.onSuccess)
                .onFailure(this.onFailure)
                .getOrNull());
    }

    protected abstract Supplier<AGGREGATION> doExecute();
}
