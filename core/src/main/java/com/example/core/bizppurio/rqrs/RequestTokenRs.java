package com.example.core.bizppurio.rqrs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTokenRs {
    private String accesstoken;
    private String type;
    private String expired;

    public LocalDateTime getExpiredDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        return LocalDateTime.parse(this.expired, formatter);
    }

}