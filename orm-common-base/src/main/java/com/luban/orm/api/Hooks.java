package com.luban.orm.api;

import java.util.function.Consumer;

/**
 * @author hp
 */
public interface Hooks<AGGREGATE_ROOT> extends Executor<AGGREGATE_ROOT>{

    Hooks<AGGREGATE_ROOT> onSuccess(Consumer<AGGREGATE_ROOT> consumer);

    Hooks<AGGREGATE_ROOT> onFailure(Consumer<? super Throwable> consumer);

}
