package com.example.gpt.application.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GptChoice {
    private int index;
    private GptMessage gptMessage;
}