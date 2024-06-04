package com.hp.common.base.deserializer.jackson;

import cn.hutool.core.date.DateUtil;
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
public final class FromStringDeserializer {

    private static Optional<String> getString(JsonParser p) throws IOException {
        return Optional.ofNullable(p.readValueAs(String.class));
    }

    public static class ToLocalDateTime extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return getString(p).map(LocalDateTimeUtil::parse).orElse(null);
        }
    }

    public static class ToLocalDate extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return getString(p).map(LocalDateTimeUtil::parseDate).orElse(null);
        }
    }

    public static class ToInstant extends JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return getString(p).map(s -> DateUtil.toInstant(DateUtil.parse(s))).orElse(null);
        }
    }

    public static class ToDate extends JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return getString(p).map(DateUtil::parse).orElse(null);
        }
    }

}
