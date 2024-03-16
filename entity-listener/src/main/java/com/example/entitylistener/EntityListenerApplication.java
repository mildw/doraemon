package com.example.entitylistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

@EnableJpaAuditing
@SpringBootApplication
public class EntityListenerApplication extends SpringBootServletInitializer {


    @PostConstruct
    public void init() {
        // 초기화 메서드 수행
    }
    public static void main(String[] args) {
        SpringApplication.run(EntityListenerApplication.class, args);
    }

}
