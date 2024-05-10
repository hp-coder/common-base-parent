package com.luban.common.base.pipeline.support;

import com.luban.common.base.pipeline.PipelineContext;
import com.luban.common.base.pipeline.PipelineFilter;
import com.luban.common.base.pipeline.PipelineFilterChain;

/**
 * @author hp
 */
public abstract class AbstractPipelineFilter<CONTEXT extends PipelineContext> implements PipelineFilter<CONTEXT> {

    @Override
    public void doFilter(CONTEXT context, PipelineFilterChain<CONTEXT> chain) {
        if (context.getFilterSelector().match(this.getClass().getSimpleName())) {
            this.doFilterInternal(context);
        }
        if (context.isContinuous()) {
            chain.next(context);
        }
    }

    protected abstract void doFilterInternal(CONTEXT context);
}
