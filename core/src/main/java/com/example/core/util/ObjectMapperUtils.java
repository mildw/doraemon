package com.example.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectMapperUtils {

    private static final ObjectMapper objectMapper = initObjectMapper();

    private static ObjectMapper initObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        T destination;
        try {
            destination = objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return destination;
    }

    public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
        T destination;
        try {
            destination = objectMapper.readValue(content, valueTypeRef);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return destination;
    }

    public static String writeValueAsString(Object value) {
        String destination;
        try {
            destination = objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return destination;
    }

}