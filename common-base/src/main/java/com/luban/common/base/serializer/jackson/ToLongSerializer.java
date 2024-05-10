package com.luban.common.base.serializer.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author hp
 */
public final class ToLongSerializer {

    public static class FromLocalDateTime extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (Objects.isNull(value)) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeNumber(value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
        }
    }

    public static class FromLocalDate extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (Objects.isNull(value)) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeNumber(value.atTime(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
        }
    }

    public static class FromInstant extends JsonSerializer<Instant> {
        @Override
        public void serialize(Instant value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (Objects.isNull(value)) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeNumber(value.toEpochMilli());
            }
        }
    }

    public static class FromDate extends JsonSerializer<Date> {
        @Override
        public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (Objects.isNull(value)) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeNumber(value.toInstant().toEpochMilli());
            }
        }
    }

}
