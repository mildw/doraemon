package com.example.multitenancy.config.security;

import com.example.multitenancy.aop.TenantFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer configure() {

        return (web) -> web.ignoring().antMatchers(
                "/resources/**",
                "/static/**",
                "/robots.txt"
        );
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests().anyRequest().permitAll();

        http.addFilterAfter(new TenantFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
