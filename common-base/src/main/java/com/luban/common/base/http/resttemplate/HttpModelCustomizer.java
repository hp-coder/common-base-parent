package com.luban.common.base.http.resttemplate;

import cn.hutool.core.util.StrUtil;
import com.luban.common.base.model.Request;
import com.luban.common.base.utils.HttpHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.List;

public interface HttpModelCustomizer<T, E> {
    RequestEntity<E> customize(T data, HttpModel httpModel);

    static HttpModelCustomizer<Request, MultiValueMap<String, String>> wwwFormUrlencodedCustomizer() {
        return (data, httpModel) -> {
            final String queryString = HttpHelper.entity2QueryString(data);
            final URI uri = URI.create(httpModel.getUrl() + "?" + queryString);
            final HttpHeaders httpHeaders = new HttpHeaders();
            httpModel.getHeaders().forEach((key, value) -> httpHeaders.addAll(key, List.of(value.split(StrUtil.COMMA))));
            return new RequestEntity<>(httpHeaders, HttpMethod.valueOf(httpModel.getMethod()), uri);
        };
    }
}
