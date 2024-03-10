package com.example.requestapi.application;

import com.example.requestapi.application.gpt.dto.GeminiRq;
import com.example.requestapi.application.gpt.dto.GeminiRs;
import com.example.requestapi.config.ApiKeyProperty;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class RestTemplateService {

    private final ApiKeyProperty apiKeyProperty;
    private final RestTemplate restTemplate;

    public String request(String prompt) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<GeminiRq> request = new HttpEntity<>(new GeminiRq(prompt), headers);

        ResponseEntity<GeminiRs> response = restTemplate.postForEntity(getGeminiUri(), request, GeminiRs.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return response.getStatusCode().toString();
        }

        GeminiRs rs = response.getBody();
        return rs.getCandidates().get(0).getContent().getParts().get(0).getText();
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
