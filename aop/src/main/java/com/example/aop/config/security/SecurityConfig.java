package com.example.aop.config.security;

import com.example.aop.aop.RoleFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer configure() {

        return (web) -> web.ignoring().antMatchers(
                "/health",
                "/robots.txt",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/api-docs/**"
        );
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests().anyRequest().permitAll();

        http.addFilterAfter(new RoleFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}