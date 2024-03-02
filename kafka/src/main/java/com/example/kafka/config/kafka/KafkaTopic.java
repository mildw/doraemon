package com.example.kafka.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;

public class KafkaTopic {
    public static final String TEST_TOPIC = "test.topic";

    public static NewTopic getNewTopic(String topic) {
        final int numPartitions = 1;
        final short replicationFactor = 1;

        return new NewTopic(topic, numPartitions, replicationFactor);
    }
}
