package com.example.core.bizppurio.rqrs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRs {

    private String code;
    private String description;
    private String refkey;
    private String messagekey;

}
