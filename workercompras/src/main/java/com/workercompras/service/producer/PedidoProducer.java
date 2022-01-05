package com.workercompras.service.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workercompras.model.Cartao;
import com.workercompras.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;
    private final ObjectMapper mapper;

    @SneakyThrows
    @PostMapping
    public void enviarPedido(Pedido pedido) {
        pedido.setCartao(Cartao.builder()
                        .numero("5148 5491 3016 4757")
                        .limiteDisponivel(new BigDecimal(1000))
                .build());
        rabbitTemplate.convertAndSend(queue.getName(), mapper.writeValueAsString(pedido));
        log.info("Pedido montado com sucesso em Worker Compras - PedidoProducer: {}", mapper.writeValueAsString(pedido));
    }

}
