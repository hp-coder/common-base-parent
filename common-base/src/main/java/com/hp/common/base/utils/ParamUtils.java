package com.hp.common.base.utils;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author hp 2023/4/13
 */

public interface ParamUtils {

    final class Likes {
        private Likes() {
        }

        public static String likeRight(String value) {
            return value + "%";
        }

        public static String likeLeft(String value) {
            return "%" + value;
        }

        public static String likeAll(String value) {
            return "%" + value + "%";
        }
    }

    final class Strings {
        private static final Strings EMPTY = new Strings(null);

        private final String value;

        private Strings(String value) {
            this.value = value;
        }

        public static Strings ofNullable(String value) {
            return (StrUtil.isNotEmpty(value)) ? of(value) : empty();
        }

        public static Strings of(String value) {
            return new Strings(Objects.requireNonNull(value));
        }

        public Strings map(Function<String, String> function) {
            if (notPresent()) {
                return empty();
            } else {
                return Strings.ofNullable(Objects.requireNonNull(function).apply(value));
            }
        }

        public Strings filter(Predicate<String> predicate) {
            if (notPresent()) {
                return this;
            } else {
                return Objects.requireNonNull(predicate).test(value) ? this : empty();
            }
        }

        public void ifPresent(Consumer<String> consumer) {
            if (StrUtil.isNotEmpty(value)) {
                Objects.requireNonNull(consumer).accept(value);
            }
        }

        public void ifPresentOrElse(Consumer<String> consumer, Runnable runnable) {
            if (StrUtil.isNotEmpty(value)) {
                Objects.requireNonNull(consumer).accept(value);
            } else {
                Objects.requireNonNull(runnable).run();
            }
        }

        public String orElse(String other) {
            return StrUtil.isNotEmpty(other) ? other : value;
        }

        public boolean isPresent() {
            return !notPresent();
        }

        public boolean notPresent() {
            return StrUtil.isEmpty(value);
        }

        public String get() {
            if (value == null) {
                throw new NoSuchElementException("No value present");
            }
            return value;
        }

        public static Strings empty() {
            return EMPTY;
        }
    }

    final class Collections<T extends Collection<E>, E> {
        private static final Collections<?, ?> EMPTY = new Collections<>(java.util.Collections.emptyList());

        private final T value;

        private Collections(T value) {
            this.value = value;
        }

        @SuppressWarnings("unchecked")
        public static <T extends Collection<E>, E> Collections<T, E> ofNullable(T value) {
            if (CollUtil.isEmpty(value)) {
                return (Collections<T, E>) empty();
            }
            return of(value);
        }

        public static <T extends Collection<E>, E> Collections<T, E> of(T value) {
            return new Collections<>(Objects.requireNonNull(value));
        }

        @SuppressWarnings("unchecked")
        public <R extends Collection<U>, U> Collections<R, U> map(Function<? super T, ? extends R> function) {
            if (notPresent()) {
                return (Collections<R, U>) empty();
            } else {
                return Collections.ofNullable(Objects.requireNonNull(function).apply(value));
            }
        }

        @SuppressWarnings("unchecked")
        public Collections<T, E> filter(Predicate<? super T> predicate) {
            if (notPresent()) {
                return this;
            } else {
                return Objects.requireNonNull(predicate).test(value) ? this : (Collections<T, E>) empty();
            }
        }

        public void ifPresent(Consumer<? super T> consumer) {
            if (CollUtil.isNotEmpty(value)) {
                Objects.requireNonNull(consumer).accept(value);
            }
        }

        public void ifPresentOrElse(Consumer<? super T> consumer, Runnable runnable) {
            if (CollUtil.isEmpty(value)) {
                Objects.requireNonNull(runnable).run();
            } else {
                Objects.requireNonNull(consumer).accept(value);
            }
        }

        public T orElse(T other) {
            return CollUtil.isEmpty(other) ? value : other;
        }

        public boolean notPresent() {
            return CollUtil.isNotEmpty(value);
        }

        public boolean isPresent() {
            return !notPresent();
        }

        public T get() {
            if (value == null) {
                throw new NoSuchElementException("No value present");
            }
            return value;
        }

        public static Collections<?, ?> empty() {
            return EMPTY;
        }
    }

    final class Maps<T extends Map<K, V>, K, V> {

        private static final Maps<?, ?, ?> EMPTY = new Maps<>(java.util.Collections.emptyMap());

        private final T value;

        private Maps(T value) {
            this.value = value;
        }

        @SuppressWarnings("unchecked")
        public static <T extends Map<K, V>, K, V> Maps<T, K, V> ofNullable(T value) {
            return (CollUtil.isEmpty(value)) ? ((Maps<T, K, V>) empty()) : of(value);
        }

        public static <T extends Map<K, V>, K, V> Maps<T, K, V> of(T value) {
            return new Maps<>(Objects.requireNonNull(value));
        }

        @SuppressWarnings("unchecked")
        public <NEW_M extends Map<NEW_K, NEW_V>, NEW_K, NEW_V> Maps<NEW_M, NEW_K, NEW_V> map(Function<? super T, ? extends NEW_M> function) {
            if (notPresent()) {
                return (Maps<NEW_M, NEW_K, NEW_V>) empty();
            } else {
                return Maps.ofNullable(Objects.requireNonNull(function).apply(value));
            }
        }

        @SuppressWarnings("unchecked")
        public Maps<T, K, V> filter(Predicate<? super T> predicate) {
            if (notPresent()) {
                return this;
            } else {
                return Objects.requireNonNull(predicate).test(value) ? this : (Maps<T, K, V>) empty();
            }
        }

        public void ifPresent(Consumer<? super T> consumer) {
            if (CollUtil.isNotEmpty(value)) {
                Objects.requireNonNull(consumer).accept(value);
            }
        }

        public void ifPresentOrElse(Consumer<? super T> consumer, Runnable runnable) {
            if (CollUtil.isEmpty(value)) {
                Objects.requireNonNull(runnable).run();
            } else {
                Objects.requireNonNull(consumer).accept(value);
            }
        }

        public T orElse(T other) {
            return CollUtil.isEmpty(other) ? value : other;
        }

        public boolean isPresent() {
            return !notPresent();
        }
        public boolean notPresent() {
            return CollUtil.isEmpty(value);
        }

        public T get() {
            if (value == null) {
                throw new NoSuchElementException("No value present");
            }
            return value;
        }

        public static Maps<?, ?, ?> empty() {
            return EMPTY;
        }
    }
}
