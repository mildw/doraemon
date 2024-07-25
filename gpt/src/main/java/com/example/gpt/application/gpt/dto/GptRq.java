package com.example.gpt.application.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GptRq {
    private String model;
    private List<GptMessage> messages;
    private Boolean stream;
//    private int n;
//    private double temperature;

    public GptRq(String model, boolean stream, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
//        this.n = 0;
        this.messages.add(new GptMessage("user", prompt));
        this.stream = stream;
    }
}