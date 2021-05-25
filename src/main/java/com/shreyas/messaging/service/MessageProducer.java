package com.shreyas.messaging.service;

import com.shreyas.messaging.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    private final KafkaTemplate<?, Message> kafkaTemplate;

    @Autowired
    public MessageProducer(KafkaTemplate<?, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String publishMessageToKafka(Message message) {
        logger.info("Publishing message to Kafka");
        kafkaTemplate.send("kafka_message_topic", message);
        return "Message published successfully";
    }
}
