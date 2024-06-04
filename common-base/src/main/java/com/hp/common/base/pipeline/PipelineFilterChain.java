package com.hp.common.base.pipeline;

/**
 * @author hp
 */
public interface PipelineFilterChain<CONTEXT extends PipelineContext> {

    void filter(CONTEXT context);

    void next(CONTEXT context);
}
