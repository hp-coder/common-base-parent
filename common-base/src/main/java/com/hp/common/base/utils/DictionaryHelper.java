package com.hp.common.base.utils;

import com.hp.common.base.enums.BaseEnum;
import com.hp.common.base.model.SelectResponse;
import com.hp.common.base.model.Selectable;
import lombok.experimental.UtilityClass;
import org.reflections.Reflections;

import java.util.*;

/**
 * @author hp
 */
@UtilityClass
public class DictionaryHelper {

    @SuppressWarnings("rawtypes")
    public static Collection<Class<? extends BaseEnum>> getBaseEnums(String packageName) {
        return new Reflections(Objects.requireNonNull(packageName)).getSubTypesOf(BaseEnum.class);
    }

    public static void load(String packageName, Map<String, List<SelectResponse>> cache) {
        getBaseEnums(packageName).forEach(i -> {
            if (!i.isEnum()) {
                return;
            }
            final List<SelectResponse> options = Arrays.stream(i.getEnumConstants())
                    .filter(Selectable::selectable)
                    .map(e -> new SelectResponse(e.getCode(), e.getName(), e.disabled()))
                    .toList();
            cache.put(i.getSimpleName(), options);
        });
    }
}
