package com.example.requestapi.controller;

import com.example.requestapi.application.FeignClientService;
import com.example.requestapi.application.RestTemplateService;
import com.example.requestapi.application.WebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RequestController {

    private final FeignClientService feignClientService;
    private final RestTemplateService restTemplateService;
    private final WebClientService webClientService;

    @GetMapping("/feign-client")
    public String feign(@RequestParam String prompt) {
        return feignClientService.request(prompt);
    }

    @GetMapping("/rest-template")
    public String restTemplate(@RequestParam String prompt) {
        return restTemplateService.request(prompt);
    }

    @GetMapping("/web-client")
    public String webClient(@RequestParam String prompt) {
        return webClientService.request(prompt);
    }

}
