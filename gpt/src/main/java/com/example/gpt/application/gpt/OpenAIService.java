package com.example.gpt.application.gpt;

import com.example.gpt.application.gpt.dto.GptRq;
import com.example.gpt.application.gpt.dto.OpenAIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    private final WebClient webClient;
    private final String apiKey = "api-key";

    public Flux<String> streamResponse(String prompt) {
        GptRq request = new GptRq("gpt-3.5-turbo", true, prompt);
        return webClient
                .post()
                .uri(getGptUri())
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(OpenAIResponse.class)
                .takeWhile(response -> response.getChoices() != null
                        && response.getChoices().stream().noneMatch(choice -> "stop".equals(choice.getFinish_reason())))
                .flatMap(response -> Flux.fromIterable(response.getChoices()))
                .map(choice -> choice.getDelta().getContent())
                .filter(content -> content != null && !content.isEmpty())
                .onErrorResume(e -> Flux.empty());
    }

    private URI getGptUri() {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.openai.com")
                .path("/v1/chat/completions")
                .build().toUri();
    }

}