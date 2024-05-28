package com.luban.common.base.utils;

import com.google.common.base.Preconditions;
import com.luban.common.base.model.Request;
import lombok.experimental.UtilityClass;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hp
 */
@UtilityClass
public class HttpHelper {

    public static String entity2QueryString(Request request) {
        Preconditions.checkArgument(Objects.nonNull(request));
        final Class<? extends Request> klass = request.getClass();
        final Field[] declaredFields = klass.getDeclaredFields();
        return Arrays.stream(declaredFields)
                .map(f -> {
                    try {
                        f.setAccessible(true);
                        final Object fieldValue = f.get(request);
                        if (Objects.isNull(fieldValue)) {
                            return null;
                        }
                        return f.getName() + "=" + URLEncoder.encode(String.valueOf(fieldValue), StandardCharsets.UTF_8);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Creating query string failed.", e);
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.joining("&"));
    }

    public static MultiValueMap<String, String> entity2MultiValueMap(Request request) {
        Preconditions.checkArgument(Objects.nonNull(request));
        final Class<? extends Request> klass = request.getClass();
        final Field[] declaredFields = klass.getDeclaredFields();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Arrays.stream(declaredFields)
                .forEach(f -> {
                    try {
                        f.setAccessible(true);
                        final Object fieldValue = f.get(request);
                        if (Objects.isNull(fieldValue)) {
                            return;
                        }
                        map.add(f.getName(), Base64.getUrlEncoder().withoutPadding().encodeToString(String.valueOf(fieldValue).getBytes()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Creating MultiValueMap failed.", e);
                    }
                });
        return map;
    }
}
