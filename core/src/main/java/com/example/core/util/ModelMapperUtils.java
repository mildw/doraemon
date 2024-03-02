package com.example.core.util;

import java.lang.reflect.Type;
import java.util.Objects;

import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@UtilityClass
public final class ModelMapperUtils {

    private static final ModelMapper modelMapper = initModelMapper();

    private static ModelMapper initModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    public static <D> D map(Object source, Class<D> destinationType) {
        return Objects.isNull(source) ? null : modelMapper.map(source, destinationType);
    }

    public static <D> D map(Object source, Type destinationType) {
        return Objects.isNull(source) ? null : modelMapper.map(source, destinationType);
    }

}