package com.hp.common.base.pipeline.support;

import com.hp.common.base.pipeline.PipelineContext;
import com.hp.common.base.pipeline.PipelineFilter;
import com.hp.common.base.pipeline.PipelineFilterChain;

/**
 * @author hp
 */
public class DefaultPipelineFilterChain<CONTEXT extends PipelineContext> extends AbstractPipelineFilterChain<CONTEXT> {
    public DefaultPipelineFilterChain(PipelineFilterChain<CONTEXT> next, PipelineFilter<CONTEXT> filter) {
        super(next, filter);
    }
}
