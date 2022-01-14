package com.mscompra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscompra.CompraApplication;
import com.mscompra.DadosMok;
import com.mscompra.model.Pedido;
import com.mscompra.service.PedidoService;
import com.mscompra.service.exception.EntidadeNaoEncontradaException;

import com.mscompra.service.rabbitmq.Producer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CompraApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private Producer producer;

    private static final String ROTA_PEDIDO = "/pedido";

    private DadosMok dadosMok = new DadosMok();

    @DisplayName("POST - Deve cadastrar um novo pedido com sucesso no banco de dados")
    @Test
    void deveCadastrarPedidoComSucesso() throws Exception {
        var pedidoBody = dadosMok.getPedido();
        var id = 1L;

        Mockito.doNothing().when(producer).enviarPedido(Mockito.any(Pedido.class));

        mockMvc.perform(post(ROTA_PEDIDO)
                .content(mapper.writeValueAsString(pedidoBody))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Pedido pedidoSalvo = pedidoService.buscarOuFalharPorId(id);

        assertEquals(pedidoSalvo.getId(), id);
        assertNotNull(pedidoSalvo);
    }

    @DisplayName("GET - Deve buscar o pedido com sucesso na base de dados")
    @Test
    void deveBuscarPedidoComSucesso() throws Exception {
        var id = 1L;

        mockMvc.perform(get(ROTA_PEDIDO.concat("/" + id)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("GET - Deve falhar ao buscar pedido que nao existe")
    @Test
    void deveFalharAoBuscarPedidoQueNaoExiste() throws Exception {
        var id = 2L;

        mockMvc.perform(get(ROTA_PEDIDO.concat("/" + id)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("DELETE - Deve excluir um pedido com sucesso")
    @Test
    void deveExcluirUmPedidoComSucesso() throws Exception {
        var id = 1L;

        mockMvc.perform(delete(ROTA_PEDIDO.concat("/" + id)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Throwable exception = assertThrows(EntidadeNaoEncontradaException.class, () -> pedidoService.excluir(id));

        assertEquals("O pedido de id: " + id + " nao existe na base de dados!", exception.getMessage());
    }
}
