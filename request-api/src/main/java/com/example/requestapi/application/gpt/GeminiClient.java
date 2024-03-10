package com.example.requestapi.application.gpt;

import com.example.requestapi.application.gpt.dto.GeminiRq;
import com.example.requestapi.application.gpt.dto.GeminiRs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gemini", url = "https://generativelanguage.googleapis.com")
public interface GeminiClient {

    @PostMapping("/v1beta/models/gemini-pro:generateContent")
    GeminiRs chat(
            @RequestParam("key") String key,
            @RequestBody GeminiRq rq);

}
