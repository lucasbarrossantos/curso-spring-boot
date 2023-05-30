package com.workercompras.service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workercompras.model.Pedido;
import com.workercompras.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class Consumer {

    Logger log = LogManager.getLogger(Consumer.class);

    private final ObjectMapper mapper;
    private final EmailService emailService;

    @KafkaListener(topics = {"${broker.kafka.topic}"})
    public void consumer(String in) throws IOException {
        var pedido = mapper.readValue(in, Pedido.class);
        log.info("Order received: {}", pedido);
        emailService.notificarCliente(pedido);
        log.info("Customer notified");
    }

}
