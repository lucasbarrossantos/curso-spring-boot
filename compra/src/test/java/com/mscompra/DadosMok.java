package com.mscompra;

import com.mscompra.model.Pedido;

import java.math.BigDecimal;
import java.util.Date;

public class DadosMok {

    public Pedido getPedido() {
        return Pedido.builder()
                .nome("Lucas Barros")
                .produto(1L)
                .valor(BigDecimal.TEN)
                .dataCompra(new Date())
                .cpfCliente("111.222.333-44")
                .cep("12345678")
                .email("lucas-barros28@hotmail.com")
                .build();
    }

}
