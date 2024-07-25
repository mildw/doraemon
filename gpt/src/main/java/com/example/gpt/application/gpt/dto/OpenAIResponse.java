package com.example.gpt.application.gpt.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAIResponse {

    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Object system_fingerprint;

    @ToString
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private int index;
        private Delta delta;
        private Object logprobs;
        private String finish_reason;

        @ToString
        @Getter @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Delta {
            private String role;
            private String content;
        }
    }
}