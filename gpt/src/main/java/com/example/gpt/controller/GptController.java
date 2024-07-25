package com.example.gpt.controller;

import com.example.gpt.application.gpt.GptService;
import com.example.gpt.application.gpt.OpenAIService;
import com.example.gpt.application.gpt.dto.OpenAIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.concurrent.SubmissionPublisher;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class GptController {

    private final OpenAIService openAIService;
    private final GptService gptService;

    @GetMapping("/gpt")
    public String gpt(@RequestParam String prompt) {
        return gptService.gptChat(prompt);
    }

    @GetMapping("/gemini")
    public Object gemini(@RequestParam String prompt) {
        return gptService.geminiChat(prompt);
    }

    @GetMapping("/stream")
    public Flux<String> streamOpenAI(@RequestParam String prompt) {
        return openAIService.streamResponse(prompt);
    }
}