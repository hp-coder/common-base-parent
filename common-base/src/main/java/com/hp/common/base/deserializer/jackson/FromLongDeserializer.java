package com.hp.common.base.deserializer.jackson;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * @author hp
 */
public final class FromLongDeserializer {

    private static Optional<Long> getLong(JsonParser p) throws IOException {
        return Optional.ofNullable(p.readValueAs(Long.class));
    }

    public static class ToLocalDateTime extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return getLong(p).map(LocalDateTimeUtil::of).orElse(null);
        }
    }

    public static class ToLocalDate extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return getLong(p).map(LocalDateTimeUtil::of).map(LocalDateTime::toLocalDate).orElse(null);
        }
    }

    public static class ToInstant extends JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return getLong(p).map(Instant::ofEpochMilli).orElse(null);
        }
    }

    public static class ToDate extends JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return getLong(p).map(l -> Date.from(Instant.ofEpochMilli(l))).orElse(null);
        }
    }

}
