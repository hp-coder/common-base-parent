package com.luban.orm.api;

import java.util.function.Consumer;

/**
 * @author hp
 */
public interface Hooks<AGGREGATION> extends Executor<AGGREGATION>{

    Hooks<AGGREGATION> onSuccess(Consumer<AGGREGATION> consumer);

    Hooks<AGGREGATION> onFailure(Consumer<? super Throwable> consumer);

}
