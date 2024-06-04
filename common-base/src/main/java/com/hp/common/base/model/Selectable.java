package com.hp.common.base.model;

/**
 * 主要是提供字典项选择能力, 配合前端组件使用
 *
 * @author hp
 */
public interface Selectable {

    default boolean selectable() {
        return true;
    }

    default boolean disabled() {
        return false;
    }
}
