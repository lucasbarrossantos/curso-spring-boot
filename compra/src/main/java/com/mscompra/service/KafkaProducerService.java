package com.mscompra.service;

import com.mscompra.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaConfig kafkaProperties;

    public void sendMessage(String message) {
        kafkaTemplate.send(kafkaProperties.getTopic(), message);
        kafkaTemplate.flush();
        log.info("send message to: {}", kafkaProperties.getTopic());
        log.info("order mounted: {}", message);
    }
}

