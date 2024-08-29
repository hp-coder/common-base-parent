package com.hp.common.base.http.resttemplate;

import com.hp.common.base.visitor.Visitor;
import jakarta.annotation.Nonnull;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.Proxy;

/**
 * @author hp
 */

public class RestTemplateFactory {

    public static RestTemplate createProxyRestTemplate(@Nonnull Proxy proxy) {
        return createRestTemplate(builder -> {
            final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setProxy(proxy);
            builder.requestFactory(() -> requestFactory);
        });
    }

    public static RestTemplate configureProxyRestTemplate(@Nonnull RestTemplate restTemplate, @Nonnull Proxy proxy) {
        return configureRestTemplate(restTemplate, builder -> {
            final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setProxy(proxy);
            builder.requestFactory(() -> requestFactory);
        });
    }

    public static RestTemplate createRestTemplate(@Nonnull Visitor<RestTemplateBuilder> visitor) {
        final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().defaultMessageConverters();
        visitor.visit(restTemplateBuilder);
        return restTemplateBuilder.build();
    }

    public static RestTemplate configureRestTemplate(@Nonnull RestTemplate restTemplate, @Nonnull Visitor<RestTemplateBuilder> visitor) {
        final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().defaultMessageConverters();
        visitor.visit(restTemplateBuilder);
        return restTemplateBuilder.configure(restTemplate);
    }
}
