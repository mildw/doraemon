package com.example.requestapi.application;

import com.example.requestapi.application.gpt.GeminiClient;
import com.example.requestapi.application.gpt.dto.GeminiRq;
import com.example.requestapi.application.gpt.dto.GeminiRs;
import com.example.requestapi.config.ApiKeyProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeignClientService {

    private final ApiKeyProperty apiKeyProperty;
    private final GeminiClient geminiClient;

    public String request(String prompt) {
        GeminiRs rs = geminiClient.chat(apiKeyProperty.getGpt(), new GeminiRq(prompt));
        return rs.getCandidates().get(0).getContent().getParts().get(0).getText();
    }

}
