package com.hp.common.base.model;

import cn.hutool.core.collection.CollUtil;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author hp
 */
@Getter
@Setter
public class PageResponse<T> implements Response {

    private Long total = 0L;
    private Integer size = 0;
    private Integer page = 1;
    private Collection<T> list = Collections.emptyList();

    protected PageResponse() {
    }

    protected PageResponse(Collection<T> list, Long total, Integer page, Integer size) {
        Preconditions.checkArgument(total >= 0, "total can not be less than zero.");
        Preconditions.checkArgument(page >= 0, "page can not be less than zero.");
        Preconditions.checkArgument(size >= 0, "size can not be less than zero.");
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

    public static <T> PageResponse<T> empty(PageResponse<?> pageResponse) {
        return PageResponse.empty(pageResponse.getPage(), pageResponse.getSize());
    }

    public static <T> PageResponse<T> empty(Integer page, Integer size) {
        return PageResponse.of(Collections.emptyList(), 0L, page, size);
    }

    public static <T> PageResponse<T> of(List<T> list, Long total, PageRequestWrapper<? extends Request> wrapper) {
        return PageResponse.of(list, total, wrapper.getPage(), wrapper.getSize());
    }

    public static <T> PageResponse<T> of(List<T> list, Long total, Integer page, Integer size) {
        return new PageResponse<>(list, total, page, size);
    }

    public static <T> PageResponse<T> of(PageResponse<?> pageResponse, List<T> list) {
        return new PageResponse<>(list, pageResponse.getTotal(), pageResponse.getPage(), pageResponse.getSize());
    }

    public static <T, R> PageResponse<R> of(PageResponse<T> pageResponse, Function<T, R> converter) {
        final Collection<T> list = pageResponse.getList();
        if (CollUtil.isEmpty(list)) {
            return PageResponse.empty(pageResponse);
        }
        return PageResponse.of(pageResponse, list.stream().map(converter).toList());
    }
}
