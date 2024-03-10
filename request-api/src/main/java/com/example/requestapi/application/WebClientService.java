package com.example.requestapi.application;

import com.example.requestapi.application.gpt.dto.GeminiRq;
import com.example.requestapi.application.gpt.dto.GeminiRs;
import com.example.requestapi.config.ApiKeyProperty;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class WebClientService {

    private final ApiKeyProperty apiKeyProperty;
    private final WebClient webClient;

    public String request(String prompt) {
        GeminiRs rs = webClient.post()
                .uri(getGeminiUri())
                .headers(httpHeaders -> httpHeaders.addAll(getHttpHeaders()))
                .body(Mono.just(new GeminiRq(prompt)), GeminiRq.class)
                .retrieve()
                .bodyToMono(GeminiRs.class)
                .block();

        return rs.getCandidates().get(0).getContent().getParts().get(0).getText();
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

    private URI getGeminiUri() {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("generativelanguage.googleapis.com")
                .path("/v1beta/models/gemini-pro:generateContent")
                .queryParam("key", apiKeyProperty.getGpt())
                .build().toUri();
    }
}
