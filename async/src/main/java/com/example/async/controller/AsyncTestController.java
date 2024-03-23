package com.example.async.controller;

import com.example.async.application.AsyncTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class AsyncTestController {

    private final AsyncTestService asyncTestService;

    @GetMapping()
    public void test() throws ExecutionException, InterruptedException {
        asyncTestService.test();
    }

    @GetMapping("/exception/runtime")
    public void runtimeException() throws ExecutionException, InterruptedException {
        asyncTestService.runtimeException();
    }
}
