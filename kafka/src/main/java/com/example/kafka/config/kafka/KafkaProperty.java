package com.example.kafka.config.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter @Setter
@ConfigurationProperties("spring.kafka")
public class KafkaProperty {
    private List<String> bootstrapServers;
}
