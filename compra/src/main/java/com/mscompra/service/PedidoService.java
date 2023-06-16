package com.mscompra.service;

import com.mscompra.model.Pedido;
import com.mscompra.repository.PedidoRepository;
import com.mscompra.service.event.ProducerOrder;
import com.mscompra.service.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PedidoService {
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
