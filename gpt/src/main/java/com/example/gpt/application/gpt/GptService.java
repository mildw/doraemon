package com.example.gpt.application.gpt;

import com.example.gpt.application.gpt.dto.GptRq;
import com.example.gpt.application.gpt.dto.GptRs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GptService {

    private final GptClient gptClient;

    public String chat(String prompt) {
        GptRs rs = requestGptWork(prompt);
        return rs.getChoices().get(0).getMessage().getContent();
    }

    private GptRs requestGptWork(String prompt) {
        String model = "gpt-3.5-turbo";
        String apiKey = "";
        GptRq rq = new GptRq(model, prompt);
        GptRs rs = gptClient.chat("Bearer " + apiKey, rq);
        return rs;
    }

}
