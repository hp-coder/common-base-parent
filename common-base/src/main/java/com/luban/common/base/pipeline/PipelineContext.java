package com.luban.common.base.pipeline;

/**
 * @author hp
 */
public interface PipelineContext {

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
