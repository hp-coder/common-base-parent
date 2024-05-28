package com.luban.common.base.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * @author hp
 */
@Getter
@Setter
public class PageResponse<T> implements Response {

    private Long total = 0L;
    private Integer size = 0;
    private Integer page = 1;
    private List<T> list = Collections.emptyList();

    protected PageResponse() {
    }

    protected PageResponse(List<T> list, Long total, Integer page, Integer size) {
        this.list = list;
        this.total = total;
        this.size = size;
        this.page = page;
    }

    public static <T> PageResponse<T> empty() {
        return PageResponse.empty(1, 0);
    }

    public static <T> PageResponse<T> empty(PageRequestWrapper<? extends Request> wrapper) {
        return PageResponse.empty(wrapper.getPage(), wrapper.getSize());
    }

    public static <T> PageResponse<T> empty(Integer page, Integer size) {
        return new PageResponse<>(Collections.emptyList(), 0L, page, size);
    }

    public static <T> PageResponse<T> of(List<T> list, Long total, PageRequestWrapper<? extends Request> wrapper) {
        return PageResponse.of(list, total, wrapper.getPage(), wrapper.getSize());
    }

    public static <T> PageResponse<T> of(List<T> list, Long total, Integer page, Integer size) {
        return new PageResponse<>(list, total, page, size);
    }
}
