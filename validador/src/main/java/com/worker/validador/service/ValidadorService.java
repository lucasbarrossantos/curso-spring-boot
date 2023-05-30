package com.worker.validador.service;

import com.worker.validador.model.Cartao;
import com.worker.validador.model.Pedido;
import com.worker.validador.service.exceptions.LimiteIndisponivelException;
import com.worker.validador.service.exceptions.SaldoInsuficienteException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidadorService {

    Logger log = LogManager.getLogger(ValidadorService.class);

    private final EmailService emailService;

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
