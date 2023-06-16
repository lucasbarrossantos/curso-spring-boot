package com.mscompra.service.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscompra.model.Pedido;
import com.mscompra.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProducerOrder {

    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper mapper;

    public void sendOrder(final Pedido pedido) {
        try {
            var payload = mapper.writeValueAsString(pedido);
            kafkaProducerService.sendMessage(payload);
        } catch (JsonProcessingException e) {
            log.error("wasn't possible to send order to destination: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
