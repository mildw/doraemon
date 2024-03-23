package com.example.jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test() throws InterruptedException {
        Thread.sleep(10000L);
        return "test";
    }

    @GetMapping("/exception/runtime")
    public String runtimeException() throws InterruptedException {
        throw new RuntimeException();
    }
}
