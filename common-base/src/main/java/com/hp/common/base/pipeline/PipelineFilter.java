package com.hp.common.base.pipeline;

/**
 * @author hp
 */
public interface PipelineFilter<CONTEXT extends PipelineContext> {

    void doFilter(CONTEXT context, PipelineFilterChain<CONTEXT> chain);
}
