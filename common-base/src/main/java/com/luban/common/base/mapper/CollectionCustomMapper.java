package com.luban.common.base.mapper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.mapstruct.Named;

import java.util.List;

public interface CollectionCustomMapper {

    @Named("listToCommaString")
    static String listToCommaString(List<String> list) {
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        return String.join(StrUtil.COMMA, list);
    }

    @Named("commaStringToList")
    static List<String> commaStringToList(String sequence) {
        if (StrUtil.isEmpty(sequence)) {
            return null;
        }
        return List.of(sequence.split(StrUtil.COMMA));
    }
}
