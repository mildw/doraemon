package com.example.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AopApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }

}
