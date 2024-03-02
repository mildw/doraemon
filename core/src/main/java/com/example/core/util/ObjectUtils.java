package com.example.core.util;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.function.Function;

@UtilityClass
public class ObjectUtils {

    public static <T> T getValueOrDefault(T object, T defaultValue) {
        return Objects.nonNull(object) ? object : defaultValue;
    }

    public static <T, K> K getValueOrNull(T object, Function<? super T, ? extends K> function) {
        return Objects.nonNull(object) ? function.apply(object) : null;
    }

}
