package com.example.gpt.application.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeminiContent {
    private String role;
    private List<GeminiPart> parts;

    public GeminiContent(String prompt) {
        this.parts = new ArrayList<>();
        parts.add(new GeminiPart(prompt));
    }
}
