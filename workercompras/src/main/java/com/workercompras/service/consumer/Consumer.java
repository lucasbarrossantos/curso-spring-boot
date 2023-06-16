package com.workercompras.service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workercompras.model.Pedido;
import com.workercompras.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class Consumer {

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
