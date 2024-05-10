package com.luban.orm.api;

import java.util.function.Consumer;

/**
 * @author hp
 * @date 2022/10/18
 */
public interface Updater<AGGREGATION> extends OrmOperation{

    Executor<AGGREGATION> update(Consumer<AGGREGATION> consumer);
}
