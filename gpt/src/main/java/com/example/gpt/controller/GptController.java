package com.example.gpt.controller;

import com.example.gpt.application.gpt.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GptController {

    private final GptService gptService;

    @GetMapping("/gpt")
    public String gpt(@RequestParam String prompt) {
        return gptService.chat(prompt);
    }

}
