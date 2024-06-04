package com.hp.orm.api;

import java.util.function.Consumer;

/**
 * @author hp
 */
public interface Hooks<AGGREGATE_ROOT> extends Executor<AGGREGATE_ROOT>{

    Hooks<AGGREGATE_ROOT> registerOnSuccess(Consumer<AGGREGATE_ROOT> consumer);

    Hooks<AGGREGATE_ROOT> registerOnFailure(Consumer<? super Throwable> consumer);

}
