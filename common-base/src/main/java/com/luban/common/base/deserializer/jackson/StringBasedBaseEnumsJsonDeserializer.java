package com.luban.common.base.deserializer.jackson;

import com.luban.common.base.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * @author hp
 */
@Slf4j
public abstract class StringBasedBaseEnumsJsonDeserializer<T extends Enum<T> & BaseEnum<T, String>, C extends Collection<T>> extends AbstractBaseEnumsJsonDeserializer<T, String, C> {
}
