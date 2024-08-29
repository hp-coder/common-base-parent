package com.hp.common.base.utils;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import jakarta.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author hp
 */
@UtilityClass
public class ProgrammaticHelper {

    /**
     * Return null to fire next attempt.
     */
    public static <R> R simpleRetryable(int maxAttempts, Supplier<R> supplier) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            final R r = supplier.get();
            if (Objects.nonNull(r)) {
                return r;
            }
            if (attempts++ > maxAttempts) {
                return null;
            }
        }
        return null;
    }

    /**
     * Var args to a modifiable List
     */
    @Nonnull
    public static <VALUE> List<VALUE> getSafeVarargs(VALUE value, VALUE[] values) {
        final List<VALUE> valueHolder = Lists.newArrayList(value);
        Optional.ofNullable(values).ifPresent(arr -> valueHolder.addAll(Lists.newArrayList(arr)));
        return valueHolder.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    /**
     * Throwable to String
     */
    public static String getStacktrace(Throwable throwable) {
        return Optional.ofNullable(throwable)
                .map(e -> {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    return sw.toString();
                })
                .orElse(StrUtil.EMPTY);
    }
}
