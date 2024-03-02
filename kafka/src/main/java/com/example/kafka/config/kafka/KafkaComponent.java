package com.example.kafka.config.kafka;

import com.example.kafka.config.kafka.message.TestMessage;
import com.example.kafka.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaComponent {

    private final ConfigurableEnvironment environment;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendTestMessage(TestMessage testMessage) {
        String topic = KafkaTopic.TEST_TOPIC;
        String message = ObjectMapperUtils.writeValueAsString(testMessage);
        kafkaTemplate.send(topic, message);
    }


}