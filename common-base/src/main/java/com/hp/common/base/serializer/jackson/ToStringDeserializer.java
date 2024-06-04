package com.hp.common.base.serializer.jackson;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * @author hp
 */
public final class ToStringDeserializer {

    public static class FromLocalDateTime extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (Objects.isNull(value)) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeString(LocalDateTimeUtil.format(value, DatePattern.NORM_DATETIME_FORMATTER));
            }
        }
    }

    public static class FromLocalDate extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (Objects.isNull(value)) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeString(LocalDateTimeUtil.format(value, DatePattern.NORM_DATE_FORMATTER));
            }
        }
    }

    public static class FromInstant extends JsonSerializer<Instant> {
        @Override
        public void serialize(Instant value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (Objects.isNull(value)) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeString(LocalDateTimeUtil.format(LocalDateTime.from(value), DatePattern.NORM_DATETIME_FORMATTER));
                jsonGenerator.writeString(LocalDateTimeUtil.format(LocalDateTime.from(value), DatePattern.NORM_DATETIME_FORMATTER));
            }
        }
    }

    public static class FromDate extends JsonSerializer<Date> {
        @Override
        public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (Objects.isNull(value)) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeString(DateUtil.format(value, DatePattern.NORM_DATETIME_FORMATTER));
            }
        }
    }

}
