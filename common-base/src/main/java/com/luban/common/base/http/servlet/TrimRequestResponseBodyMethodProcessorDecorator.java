package com.luban.common.base.http.servlet;

import cn.hutool.core.util.StrUtil;
import com.luban.common.base.annotation.Trim;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.Objects;
import java.util.Optional;

/**
 * Replace RequestResponseBodyMethodProcessor or add this decorator before it.
 *
 * @author hp
 * @see RequestMappingHandlerAdapter
 */
public class TrimRequestResponseBodyMethodProcessorDecorator implements HandlerMethodArgumentResolver {

    private final RequestResponseBodyMethodProcessor processor;

    public TrimRequestResponseBodyMethodProcessorDecorator(RequestResponseBodyMethodProcessor processor) {
        this.processor = processor;
    }

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return processor.supportsParameter(parameter);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final Object o = processor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        if (Objects.isNull(o)) {
            return parameter.isOptional() ? Optional.empty() : null;
        }

        Trim trim = null;
        parameter = parameter.nestedIfOptional();
        final boolean trimOnMethod = parameter.hasMethodAnnotation(Trim.class);
        trim = trimOnMethod ? parameter.getMethodAnnotation(Trim.class) : trim;

        final boolean trimOnParameter = parameter.hasParameterAnnotation(Trim.class);
        trim = trimOnParameter ? parameter.getParameterAnnotation(Trim.class) : trim;

        final boolean trimOnClass = parameter.getParameterType().isAnnotationPresent(Trim.class);
        trim = trimOnClass ? parameter.getParameterType().getAnnotation(Trim.class) : trim;

        Object object;
        if (parameter.isOptional()) {
            final Optional<?> optional = (Optional<?>) o;
            assert optional.isPresent();
            object = optional.get();
        } else {
            object = o;
        }

        Trim finalTrim = trim;
        ReflectionUtils.doWithFields(
                ((Class) parameter.getNestedGenericParameterType()),
                field -> {
                    final boolean trimOnField = AnnotatedElementUtils.hasAnnotation(field, Trim.class);
                    final Trim fieldTrim = trimOnField ? field.getAnnotation(Trim.class) : finalTrim;

                    ReflectionUtils.makeAccessible(field);
                    final String value = (String) field.get(object);
                    final String trimmedVal = StrUtil.trim(value, Optional.ofNullable(fieldTrim).map(i -> i.value().getCode()).orElseThrow());
                    field.set(object, trimmedVal);
                },
                field -> field.getType() == String.class && (trimOnMethod || trimOnParameter || trimOnClass || AnnotatedElementUtils.hasAnnotation(field, Trim.class))
        );

        return o;
    }
}
