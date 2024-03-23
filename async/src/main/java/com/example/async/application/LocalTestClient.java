package com.example.async.application;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "local", url = "http://localhost:8080")
public interface LocalTestClient {

    @GetMapping("/test")
    String test();

    @GetMapping("/test/exception/runtime")
    String runtimeException();

}
