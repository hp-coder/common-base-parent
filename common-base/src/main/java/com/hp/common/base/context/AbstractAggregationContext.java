package com.hp.common.base.context;

import lombok.Data;

/**
 * @author hp
 */
@Data
public abstract class AbstractAggregationContext<AGG_ROOT, COMMAND> implements AggregationContext<AGG_ROOT, COMMAND> {

    protected COMMAND command;

    protected AGG_ROOT entity;

    public AbstractAggregationContext() {
    }

    public AbstractAggregationContext(COMMAND command) {
        this.command = command;
    }
}
