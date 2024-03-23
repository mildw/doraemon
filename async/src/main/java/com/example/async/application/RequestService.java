package com.example.async.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestService {

    private final LocalTestClient localTestClient;

    public String request() {
        String result = localTestClient.test();
        System.out.println("method : "+result);
        return result;
    }

    public Boolean runtimeException() {
        String result = localTestClient.runtimeException();
        System.out.println("method : "+result);
        return true;
    }
}
