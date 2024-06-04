package com.hp.common.base.pipeline.support;

import com.google.common.base.Preconditions;
import com.hp.common.base.pipeline.PipelineContext;
import com.hp.common.base.pipeline.PipelineFilter;
import com.hp.common.base.pipeline.PipelineFilterChain;

import java.util.Objects;
import java.util.Optional;

/**
 * @author hp
 */
public abstract class AbstractPipelineFilterChain<CONTEXT extends PipelineContext> implements PipelineFilterChain<CONTEXT> {

    protected final PipelineFilterChain<CONTEXT> next;

    protected final PipelineFilter<CONTEXT> filter;


    public AbstractPipelineFilterChain(PipelineFilterChain<CONTEXT> next, PipelineFilter<CONTEXT> filter) {
        Preconditions.checkArgument(Objects.nonNull(filter),"Pipeline filter can not be null.");
        this.next = next;
        this.filter = filter;
    }

    @Override
    public void filter(CONTEXT context) {
        this.filter.doFilter(context, this);
    }

    @Override
    public void next(CONTEXT context) {
        Optional.ofNullable(this.next).ifPresent(chain -> chain.filter(context));
    }
}
