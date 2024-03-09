package com.example.gpt.config.feign;

import feign.Request;
import feign.Retryer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.example.gpt.application")
@Configuration
public class FeignConfig {

    @Bean
    public Request.Options options() {
        int connectTimeoutMillis = 90 * 1000;
        int readTimeoutMillis = 10000;
        return new Request.Options(connectTimeoutMillis, readTimeoutMillis);
    }

    @Bean
    public Retryer feignRetryer() {
        // 재시도 간격(ms), 최대 재시도 횟수 설정
        return new Retryer.Default(100, 1000, 5);
    }

}