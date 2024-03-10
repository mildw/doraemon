package com.example.requestapi.config.web_client;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(getHttpClient()))
                .build();
    }

    private HttpClient getHttpClient() {
        int connectTimeout = 10000;
        int readTimeout = 10000;
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout) // 서버에 연결하는 데 걸리는 최대 시간
                .responseTimeout(Duration.ofMillis(readTimeout) // 연결 풀에서 사용 가능한 연결을 클라이언트가 기다리는 최대 시간
                );
    }
}
