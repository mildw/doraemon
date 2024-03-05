package com.example.springcloudfunction.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Slf4j
@Component
public class ExampleFunction implements Function<String, String> {


    @Override
    public String apply(String request) {

        log.info(request);

        return request;
    }
}