package com.example.gpt.application.gpt;

import com.example.gpt.application.gpt.dto.GptRq;
import com.example.gpt.application.gpt.dto.GptRs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "gpt", url = "https://api.openai.com")
public interface GptClient {

    @PostMapping("/v1/chat/completions")
    GptRs chat(
            @RequestHeader("Authorization") String authorization,
            @RequestBody GptRq rq);

}
