package com.hp.common.base.enums;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.hp.common.base.CommonBaseProperties;
import com.hp.common.base.model.SelectResponse;
import com.hp.common.base.model.Selectable;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.util.*;
import java.util.function.Function;

/**
 * @author hp
 */
@RequiredArgsConstructor
public class BaseEnumRegistrar implements SmartInitializingSingleton {
    @SuppressWarnings("rawtypes")
    private static final Map<Class<? extends BaseEnum>, List<? extends BaseEnum>> CLASS_MAP = Maps.newHashMap();
    private static final Map<String, List<SelectResponse>> SELECTION_MAP = Maps.newHashMap();

    private final CommonBaseProperties.BaseEnumProperties baseEnumProperties;

    @SuppressWarnings("rawtypes")
    public void register() {
        if (StrUtil.isEmpty(baseEnumProperties.getBasePackage())) {
            return;
        }
        final Collection<Class<? extends BaseEnum>> baseEnums = getBaseEnums(baseEnumProperties.getBasePackage());

        load(baseEnums, CLASS_MAP, Function.identity(), Function.identity());
        load(baseEnums, SELECTION_MAP, Class::getSimpleName, constants -> constants.stream()
                .filter(Selectable::selectable)
                .map(SelectResponse::new)
                .toList()
        );
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.register();
    }

    @SuppressWarnings("rawtypes")
    private static Collection<Class<? extends BaseEnum>> getBaseEnums(String packageName) {
        return new Reflections(Objects.requireNonNull(packageName)).getSubTypesOf(BaseEnum.class);
    }

    @SuppressWarnings("rawtypes")
    private static <KEY, VALUE> void load(Collection<Class<? extends BaseEnum>> baseEnums,
                                          Map<KEY, VALUE> cache,
                                          Function<Class<? extends BaseEnum>, KEY> keyFunction,
                                          Function<List<? extends BaseEnum>, VALUE> valueFunction
    ) {
        if (CollUtil.isEmpty(baseEnums)) {
            return;
        }
        baseEnums.forEach(i -> {
            if (!i.isEnum()) {
                return;
            }
            cache.put(keyFunction.apply(i), valueFunction.apply(Arrays.stream(i.getEnumConstants()).toList()));
        });
    }

    public boolean contains(Class<?> type) {
        return CLASS_MAP.containsKey(type);
    }

    @SuppressWarnings("rawtypes")
    public Collection<Class<? extends BaseEnum>> getBaseEnumTypes() {
        return CLASS_MAP.keySet();
    }

    @SuppressWarnings("rawtypes")
    public List<? extends BaseEnum> get(Class<?> type) {
        return CLASS_MAP.get(type);
    }

    public List<SelectResponse> get(String simpleName) {
        return SELECTION_MAP.get(simpleName);
    }

}
