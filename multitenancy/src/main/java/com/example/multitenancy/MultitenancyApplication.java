package com.example.multitenancy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class MultitenancyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MultitenancyApplication.class, args);
    }

}
