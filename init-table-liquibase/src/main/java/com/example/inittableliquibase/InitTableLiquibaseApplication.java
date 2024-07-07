package com.example.inittableliquibase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class InitTableLiquibaseApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(InitTableLiquibaseApplication.class, args);
    }

}
