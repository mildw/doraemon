package com.example.core.bizppurio;

import com.example.core.bizppurio.rqrs.RequestTokenRs;
import com.example.core.bizppurio.rqrs.SendMessageRq;
import com.example.core.bizppurio.rqrs.SendMessageRs;
import com.example.core.enums.Delimiter;
import lombok.experimental.UtilityClass;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@UtilityClass
public class KakaoNoticeUtils {

    private String baseUrl = "https://api.bizppurio.com";
    private final String dev_baseUrl = "https://dev-api.bizppurio.com";

    private final String requestTokenUri = "/v1/token";
    private final String sendMessageUri = "/v3/message";

    private String CONTENT_TYPE = "application/json; charset=utf-8";

    public static RequestTokenRs requestToken(Bizppurio bizppurio) {
        String authorization = requestTokenAuthorization(bizppurio);

        WebClient webClient = getWebClient(authorization);

        return webClient.post()
                .uri(requestTokenUri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(RequestTokenRs.class)
                .block();
    }

    private static String requestTokenAuthorization(Bizppurio bizppurio) {
        final String AUTHORIZATION_PREFIX = "Basic";

        String credential = Delimiter.COLON.join(bizppurio.getAccount(), bizppurio.getPassword());
        String credentialBase64 = Base64.getEncoder().encodeToString(credential.getBytes());
        String authorization = Delimiter.SPACE.join(AUTHORIZATION_PREFIX, credentialBase64);

        return authorization;
    }

    public static SendMessageRs sendMessage(Environment environment, RequestTokenRs token, SendMessageRq rq) {
        String authorization = Delimiter.SPACE.join(token.getType(), token.getAccesstoken());

        WebClient webClient = getWebClient(authorization);

        return webClient.post()
                .uri(sendMessageUri)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(rq)
                .retrieve()
                .bodyToMono(SendMessageRs.class)
                .block();
    }

    private static WebClient getWebClient(String authorization) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, authorization);
                })
                .build();

        return webClient;
    }

}
