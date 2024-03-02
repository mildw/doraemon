package com.example.kafka.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectMapperUtils {

    private static final ObjectMapper objectMapper = initMailObjectMapper();

    private static ObjectMapper initMailObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);

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
