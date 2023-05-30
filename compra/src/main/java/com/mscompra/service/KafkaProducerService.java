package com.mscompra.service;

import com.mscompra.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaConfig kafkaProperties;

    public void sendMessage(String message) {
        kafkaTemplate.send(kafkaProperties.getTopic(), message);
        kafkaTemplate.flush();
        logger.info("send message to: {}", kafkaProperties.getTopic());
    }
}

