package com.mscompra.service;

import com.mscompra.model.Pedido;
import com.mscompra.repository.PedidoRepository;
import com.mscompra.service.event.ProducerOrder;
import com.mscompra.service.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PedidoService {
    Logger log = LogManager.getLogger(PedidoService.class);
    private final PedidoRepository pedidoRepository;
    private final ProducerOrder producerOrder;

    public Pedido salvar(Pedido pedido) {
        pedido = pedidoRepository.save(pedido);
        log.info("order persisted successfully");
        producerOrder.sendOrder(pedido);
        return pedido;
    }

    public Pedido buscarOuFalharPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(()-> new EntidadeNaoEncontradaException("Order id: " + id + " does not exist in database!"));
    }

    public void excluir(Long id) {
        Pedido pedido = buscarOuFalharPorId(id);
        pedidoRepository.deleteById(pedido.getId());
    }

}
