package com.hp.common.base.context;

/**
 * @author hp
 */
public interface AggregationContext<AGG_ROOT, COMMAND> extends Context {

    COMMAND getCommand();

    AGG_ROOT getEntity();

}
