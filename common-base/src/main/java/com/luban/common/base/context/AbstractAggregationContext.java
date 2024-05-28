package com.luban.common.base.context;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hp
 */
@Data
public abstract class AbstractAggregationContext<AGG_ROOT, COMMAND> implements AggregationContext<AGG_ROOT, COMMAND> {

    @Getter
    @Setter(AccessLevel.NONE)
    protected final COMMAND command;

    protected AGG_ROOT entity;

    public AbstractAggregationContext(COMMAND command) {
        this.command = command;
    }
}
