package com.workercompras.service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workercompras.model.Cartao;
import com.workercompras.model.Pedido;
import com.workercompras.service.CartaoService;
import com.workercompras.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PedidoProducer {

    private final ObjectMapper mapper;
    private final CartaoService cartaoService;

    private final KafkaProducerService kafkaProducerService;

    public void sendOrder(Pedido pedido) throws JsonProcessingException {
        pedido.setCartao(Cartao.builder()
                        .numero(cartaoService.gerarCartao())
                        .limiteDisponivel(cartaoService.gerarLimite())
                .build());
        kafkaProducerService.sendMessage(mapper.writeValueAsString(pedido));
        log.info("Order mounted successfully - order: {}", mapper.writeValueAsString(pedido));
    }

}
