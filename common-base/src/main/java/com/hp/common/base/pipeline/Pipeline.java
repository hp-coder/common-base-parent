package com.hp.common.base.pipeline;

/**
 * @author hp
 */
public interface Pipeline<FILTER extends PipelineFilter<CONTEXT>, CONTEXT extends PipelineContext> {

    PipelineFilterChain<CONTEXT> getFilterChain();

    PipelineFilterChain<CONTEXT> addFilter(FILTER filter);

    PipelineFilterChain<CONTEXT> addFilter(FILTER filter, String description);

}
