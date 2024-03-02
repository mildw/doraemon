package com.example.core.jwt;


import io.jsonwebtoken.Claims;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Getter
public class TokenInfo {

    private String audience;
    private Map<String, Object> claims;
    private Date expiration;
    private String token;

    public TokenInfo(String audience, Map<String, Object> claims, Date expiration, String token) {
        this.audience = audience;
        this.claims = claims;
        this.expiration = expiration;
        this.token = token;
    }

}
