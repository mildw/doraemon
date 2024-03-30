package com.example.async.config.feign;

import feign.Request;
import feign.Retryer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

import java.util.concurrent.TimeUnit;

@EnableFeignClients(basePackages = "com.example.async.application")
@Configuration
public class FeignClientConfig {

    @Bean
    public FeignFormatterRegistrar localDateFeignFormatterRegister() {
        // local date time 사용시 문제가 될 수 있다. Iso 변환 처리
        return registry -> {
            DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
            registrar.setUseIsoFormat(true);
            registrar.registerFormatters(registry);
        };
    }

    @Bean
    public Request.Options options() {
        int connectTimeout = 30000;
        int readTimeout = 30000;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;

        return new Request.Options(
                connectTimeout, timeUnit, // 서버에 연결 하는 데 걸리는 최대 시간
                readTimeout, timeUnit, // 서버로 부터 응답을 받는 데 걸리는 최대 시간
                true);
    }

    @Bean
    public Retryer feignRetryer() {
        // 재시도 간격(ms), 최대 재시도 횟수 설정
        return new Retryer.Default(100, 1000, 1);
    }

}