package com.workercompras.service;

import com.workercompras.config.KafkaConfigProperties;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    Logger log = LogManager.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaConfigProperties kafkaProperties;

    public void sendMessage(String message) {
        kafkaTemplate.send(kafkaProperties.getTopicValidator(), message);
        log.info("send message to: {}", kafkaProperties.getTopic());
    }
}

