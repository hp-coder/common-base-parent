package com.luban.common.base.pipeline.support;

import cn.hutool.core.util.StrUtil;
import com.luban.common.base.pipeline.Pipeline;
import com.luban.common.base.pipeline.PipelineContext;
import com.luban.common.base.pipeline.PipelineFilter;
import com.luban.common.base.pipeline.PipelineFilterChain;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hp
 */
@Slf4j
public abstract class AbstractPipeline<FILTER extends PipelineFilter<CONTEXT>, CONTEXT extends PipelineContext> implements Pipeline<FILTER, CONTEXT> {

    protected PipelineFilterChain<CONTEXT> last;

    @Override
    public PipelineFilterChain<CONTEXT> getFilterChain() {
        return this.last;
    }

    @Override
    public PipelineFilterChain<CONTEXT> addFilter(FILTER filter) {
        return addFilter(filter, StrUtil.EMPTY);
    }

    @Override
    public PipelineFilterChain<CONTEXT> addFilter(FILTER filter, String description) {
        log.info("PipelineFilter={}[{}] has registered", filter.getClass().getSimpleName(), description);
        return new DefaultPipelineFilterChain<>(this.last, filter);
    }
}
