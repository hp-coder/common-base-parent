package com.luban.common.base.pipeline.support;

import com.luban.common.base.pipeline.PipelineContext;
import com.luban.common.base.pipeline.PipelineFilter;
import com.luban.common.base.pipeline.PipelineFilterChain;

/**
 * @author hp
 */
public class DefaultPipelineFilterChain<CONTEXT extends PipelineContext> extends AbstractPipelineFilterChain<CONTEXT> {
    public DefaultPipelineFilterChain(PipelineFilterChain<CONTEXT> next, PipelineFilter<CONTEXT> filter) {
        super(next, filter);
    }
}
