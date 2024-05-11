package com.luban.common.base.http.servlet;

import cn.hutool.core.util.StrUtil;
import com.luban.common.base.annotation.Trim;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
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
 * <p>
 * Consider this example of configuring the Decorator
 *
 * <pre>
 *
 * {@code @RequiredArgsConstructor}
 * {@code @Configuration}
 * public class HandlerMethodArgumentResolverAutoConfiguration {
 *
 *     private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;
 *     private final List<HttpMessageConverter<?>> converters;
 *
 *     {@code @PostConstruct}
 *     public void setRequestExcelArgumentResolver() {
 *         List<HandlerMethodArgumentResolver> argumentResolvers = this.requestMappingHandlerAdapter.getArgumentResolvers();
 *         List<HandlerMethodArgumentResolver> resolverList = new ArrayList<>();
 *         resolverList.add(new TrimRequestResponseBodyMethodProcessorDecorator(new RequestResponseBodyMethodProcessor(converters)));
 *         assert argumentResolvers != null;
 *         resolverList.addAll(argumentResolvers);
 *         this.requestMappingHandlerAdapter.setArgumentResolvers(resolverList);
 *     }
 * }
 *
 * </pre>
 *
 * @author hp
 * @see RequestMappingHandlerAdapter
 */
public class TrimRequestResponseBodyMethodProcessorDecorator implements HandlerMethodArgumentResolver {

    private final RequestResponseBodyMethodProcessor processor;

    public TrimRequestResponseBodyMethodProcessorDecorator(@NonNull RequestResponseBodyMethodProcessor processor) {
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

        // 开关
        parameter = parameter.nestedIfOptional();
        if (!parameter.hasParameterAnnotation(Trim.class)) {
            return o;
        }

        // 拿到真实数据对象, 因为原生支持Optional封装
        Object object;
        if (parameter.isOptional()) {
            final Optional<?> optional = (Optional<?>) o;
            assert optional.isPresent();
            object = optional.get();
        } else {
            object = o;
        }

        // 范型情况, 找到真实的对象
        Class<?> targetClass;
        if (parameter.getNestedGenericParameterType() instanceof Class<?> clazz) {
            targetClass = clazz;
        } else {
            ResolvableType resolvableType = ResolvableType.forMethodParameter(parameter);
            targetClass = resolvableType.resolve();
        }

        if (Objects.isNull(targetClass)) {
            return o;
        }

        trimObject(targetClass, object);

        return o;
    }

    private static void trimObject(Class<?> targetClass, Object object) {
        if (Objects.isNull(targetClass) || targetClass == Object.class) {
            return;
        }

        ReflectionUtils.doWithFields(
                targetClass,
                field -> {
                    final Trim fieldTrim = field.getAnnotation(Trim.class);
                    assert fieldTrim != null;

                    if (field.getType() == String.class) {
                        ReflectionUtils.makeAccessible(field);
                        final String stringVal = (String) field.get(object);
                        final String trimmedVal = StrUtil.trim(stringVal, fieldTrim.value().getCode());
                        field.set(object, trimmedVal);

                    } else {
                        ReflectionUtils.makeAccessible(field);
                        final Object value = field.get(object);
                        // 从实际对象取, 避免范型问题
                        final Class<?> fieldClass = value.getClass();
                        trimObject(fieldClass, value);
                    }
                },
                field -> (AnnotatedElementUtils.hasAnnotation(field, Trim.class))
        );
    }
}
