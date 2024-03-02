package com.example.kafka.config.kafka;

import com.example.kafka.config.kafka.message.TestMessage;
import com.example.kafka.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListeners {
    @KafkaListener(topics = KafkaTopic.TEST_TOPIC)
    public void test(String message, Acknowledgment ack) {
        TestMessage rs = ObjectMapperUtils.readValue(message, TestMessage.class);

        log.info(rs.toString());
        
        ack.acknowledge();
    }
}
