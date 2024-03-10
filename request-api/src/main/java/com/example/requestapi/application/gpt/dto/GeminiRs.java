package com.example.requestapi.application.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRs {
    private List<GeminiCandidate> candidates;
    private GeminiPromptFeedback promptFeedback;
}
