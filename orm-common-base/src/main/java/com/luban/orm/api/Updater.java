package com.luban.orm.api;

import java.util.function.Consumer;

/**
 * @author hp
 */
public interface Updater<AGGREGATE_ROOT> extends OrmOperation{

    Executor<AGGREGATE_ROOT> update(Consumer<AGGREGATE_ROOT> consumer);
}
