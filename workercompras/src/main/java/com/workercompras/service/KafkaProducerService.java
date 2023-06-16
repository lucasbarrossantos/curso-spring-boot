package com.workercompras.service;

import com.workercompras.config.KafkaConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaConfigProperties kafkaProperties;

    public void sendMessage(String message) {
        kafkaTemplate.send(kafkaProperties.getTopicValidator(), message);
        log.info("send message to: {}", kafkaProperties.getTopic());
    }
}

