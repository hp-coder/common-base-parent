package com.luban.common.base.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hp
 */
@Getter
@Setter
public class PageResponse<T> implements Response {

    private Long total;
    private Integer size;
    private Integer page;
    private List<T> list;

    protected PageResponse() {
    }

    protected PageResponse(List<T> list, Long total, Integer page, Integer size) {
        this.list = list;
        this.total = total;
        this.size = size;
        this.page = page;
    }

    public static <T> PageResponse<T> of(List<T> list, Long total, PageRequestWrapper<? extends Request> wrapper) {
        return new PageResponse<>(list, total, wrapper.getPage(), wrapper.getSize());
    }

    public static <T> PageResponse<T> of(List<T> list, Long total, Integer page, Integer size) {
        return new PageResponse<>(list, total, page, size);
    }
}
