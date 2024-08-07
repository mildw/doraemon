package com.example.gpt.application.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GptRs {
    private String id;
    private String object;
    private String created;
    private String model;
    private List<GptChoice> choices;
    private GptUsage usage;
}