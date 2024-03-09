package com.example.gpt.application.gpt;

import com.example.gpt.application.gpt.dto.GeminiRq;
import com.example.gpt.application.gpt.dto.GeminiRs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "gemini", url = "https://generativelanguage.googleapis.com")
public interface GeminiClient {

    @PostMapping("/v1beta/models/gemini-pro:generateContent")
    GeminiRs chat(
            @RequestParam("key") String key,
            @RequestBody GeminiRq rq);

}
