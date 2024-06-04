package com.hp.common.base.pipeline;

import com.hp.common.base.context.Context;

/**
 * @author hp
 */
public interface PipelineContext extends Context {

    default PipelineFilterSelector getFilterSelector() {
        return new PipelineFilterSelector() {
            @Override
            public <T> boolean match(T t) {
                return true;
            }
        };
    }

    default boolean isContinuous() {
        return true;
    }
}
