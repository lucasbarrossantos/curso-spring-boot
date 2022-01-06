package com.worker.validador.service;

import com.worker.validador.model.Cartao;
import com.worker.validador.model.Pedido;
import com.worker.validador.service.exceptions.LimiteIndisponivelException;
import com.worker.validador.service.exceptions.SaldoInsuficienteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ValidadorService {

    @Autowired
    private EmailService emailService;

    public void validarPedido(Pedido pedido) {
        validarLimiteDisponivel(pedido.getCartao());
        validarCompraComLimite(pedido);
        emailService.notificarClienteCompraComSucesso(pedido.getEmail());
    }

    private void validarCompraComLimite(Pedido pedido) {
        if (pedido.getValor().longValue() > pedido.getCartao().getLimiteDisponivel().longValue()) {
            log.error("Valor do pedido: {}. Limite disponivel: {}", pedido.getValor(), pedido.getCartao().getLimiteDisponivel());
            throw new SaldoInsuficienteException("Voce nao tem limite para efetuar essa compra!");
        }
    }

    private void validarLimiteDisponivel(Cartao cartao) {
        if (cartao.getLimiteDisponivel().longValue() <= 0)
            throw new LimiteIndisponivelException("Limite indisponivel!");
    }
}
