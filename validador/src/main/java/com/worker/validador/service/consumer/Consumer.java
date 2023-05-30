package com.worker.validador.service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worker.validador.model.Pedido;
import com.worker.validador.service.ValidadorService;
import com.worker.validador.service.exceptions.LimiteIndisponivelException;
import com.worker.validador.service.exceptions.SaldoInsuficienteException;
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
    private final ValidadorService validadorService;

    @KafkaListener(topics = {"${broker.kafka.topic}"})
    public void orderConsumer(String in) throws IOException {
        var order = mapper.readValue(in, Pedido.class);
        log.info("order received");

        try {
            validadorService.validarPedido(order);
        } catch (LimiteIndisponivelException | SaldoInsuficienteException exception) {
            exception.printStackTrace();
        }
    }

}
