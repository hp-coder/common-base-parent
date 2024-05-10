package com.luban.common.base.deserializer.jackson;

import com.luban.common.base.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hp
 */
@Slf4j
public abstract class IntegerBasedBaseEnumJsonDeserializer<T extends Enum<T> & BaseEnum<T, Integer>> extends AbstractBaseEnumJsonDeserializer<T, Integer> {
}
