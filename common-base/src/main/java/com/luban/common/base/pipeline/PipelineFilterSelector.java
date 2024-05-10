package com.luban.common.base.pipeline;

/**
 * @author hp
 */
public interface PipelineFilterSelector {

    <T> boolean match(T t);
}
