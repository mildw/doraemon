package com.example.gpt.application.gpt;

import com.example.gpt.application.gpt.dto.GeminiRq;
import com.example.gpt.application.gpt.dto.GeminiRs;
import com.example.gpt.application.gpt.dto.GptRq;
import com.example.gpt.application.gpt.dto.GptRs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GptService {

    private final GptClient gptClient;
    private final GeminiClient geminiClient;

    public String gptChat(String prompt) {
        GptRs rs = requestGptWork(prompt);
        return rs.getGptChoices().get(0).getGptMessage().getContent();
    }

    public String geminiChat(String prompt) {
        GeminiRs rs = requestGeminiWork(prompt);
        return rs.getCandidates().get(0).getContent().getParts().get(0).getText();
    }

    private GptRs requestGptWork(String prompt) {
        String model = "gpt-3.5-turbo";
        String apiKey = "";
        GptRq rq = new GptRq(model, prompt);
        GptRs rs = gptClient.chat("Bearer " + apiKey, rq);
        return rs;
    }

    private GeminiRs requestGeminiWork(String prompt) {
        String model = "generateContent";
        String apiKey = "";
        GeminiRq rq = new GeminiRq(prompt);
        GeminiRs rs = geminiClient.chat(model, apiKey, rq);
        return rs;
    }

}
