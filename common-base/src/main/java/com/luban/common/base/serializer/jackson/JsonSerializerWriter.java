package com.luban.common.base.serializer.jackson;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author hp
 */
public interface JsonSerializerWriter {

    <VALUE> void write(JsonGenerator jsonGenerator, VALUE value);

    class DefaultStringJsonSerializerWriter implements JsonSerializerWriter {

        @Override
        public <VALUE> void write(JsonGenerator jsonGenerator, VALUE value) {
            try {
                jsonGenerator.writeString(String.valueOf(value));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    class DefaultJsonSerializerWriter implements JsonSerializerWriter {

        @Override
        public <VALUE> void write(JsonGenerator jsonGenerator, VALUE value) {
            try {
                if (value instanceof Integer) {
                    jsonGenerator.writeNumber((Integer) value);
                } else if (value instanceof Long) {
                    jsonGenerator.writeNumber((Long) value);
                } else if (value instanceof BigInteger) {
                    jsonGenerator.writeNumber((BigInteger) value);
                } else if (value instanceof Double) {
                    jsonGenerator.writeNumber((Double) value);
                } else if (value instanceof Float) {
                    jsonGenerator.writeNumber((Float) value);
                } else if (value instanceof BigDecimal) {
                    jsonGenerator.writeNumber((BigDecimal) value);
                } else if (value instanceof String) {
                    jsonGenerator.writeString((String) value);
                } else {
                    jsonGenerator.writeString(String.valueOf(value));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
