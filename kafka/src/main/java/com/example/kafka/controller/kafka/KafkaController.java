package com.example.kafka.controller.kafka;

import com.example.kafka.config.kafka.KafkaComponent;
import com.example.kafka.config.kafka.message.TestMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaComponent kafkaComponent;

    @GetMapping("/test")
    public void produce() {
        kafkaComponent.sendTestMessage(new TestMessage("test"));
    }

}
