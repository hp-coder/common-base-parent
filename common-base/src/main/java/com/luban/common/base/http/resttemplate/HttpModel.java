package com.luban.common.base.http.resttemplate;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class HttpModel {

    private String url;

    private Map<String, String> headers;

    private String method = HttpMethod.POST.name();

    public <T> RequestEntity<T> createHttpEntity(T data) {
        return createHttpEntity(data, (t, httpModel) -> {
            final HttpMethod httpMethod = HttpMethod.valueOf(method);
            if (CollUtil.isNotEmpty(httpModel.headers)) {
                final HttpHeaders httpHeaders = new HttpHeaders();
                headers.forEach((key, value) -> httpHeaders.addAll(key, List.of(value.split(","))));
                if (Objects.nonNull(t)) {
                    return new RequestEntity<>(t, httpHeaders, httpMethod, URI.create(url));
                } else {
                    return new RequestEntity<>(httpHeaders, httpMethod, URI.create(url));
                }
            } else {
                if (Objects.nonNull(t)) {
                    return new RequestEntity<>(t, httpMethod, URI.create(url));
                } else {
                    return new RequestEntity<>(httpMethod, URI.create(url));
                }
            }
        });
    }

    public <T, E> RequestEntity<E> createHttpEntity(T data, HttpModelCustomizer<T, E> visitor) {
        return visitor.customize(data, this);
    }
}

