package com.mscompra.service.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscompra.model.Pedido;
import com.mscompra.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProducerOrder {

    Logger log = LogManager.getLogger(ProducerOrder.class);

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
