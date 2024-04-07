package com.example.excel.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@ConfigurationProperties("bucket")
public class BucketProperty {
    private String name;
    private String accessKey;
    private String secretKey;
}
