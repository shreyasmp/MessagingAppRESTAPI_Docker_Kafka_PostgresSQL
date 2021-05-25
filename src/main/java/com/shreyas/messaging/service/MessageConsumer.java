package com.shreyas.messaging.service;

import com.shreyas.messaging.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @KafkaListener(topics = "kafka_message_topic", groupId = "kafka_group_id")
    public void consumeFromKafkaTopic(Message message) {
        logger.info(String.format("Consumed Message is = %s", message));
    }
}
