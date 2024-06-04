package com.hp.common.base.pipeline;

/**
 * @author hp
 */
public interface PipelineFilterSelector {

    <T> boolean match(T t);
}
