package com.hp.orm.api;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author hp
 */
public interface BatchHooks<AGGREGATE_ROOT> extends BatchExecutor<AGGREGATE_ROOT>{

    BatchHooks<AGGREGATE_ROOT> registerOnSuccess(Consumer<Collection<AGGREGATE_ROOT>> consumer);

    BatchHooks<AGGREGATE_ROOT> registerOnFailure(Consumer<? super Throwable> consumer);

}
