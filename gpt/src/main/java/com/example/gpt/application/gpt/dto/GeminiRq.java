package com.example.gpt.application.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class GeminiRq {
    private List<GeminiContent> contents;

    public GeminiRq(String prompt) {
        this.contents = new ArrayList<>();
        contents.add(new GeminiContent(prompt));
    }
}
