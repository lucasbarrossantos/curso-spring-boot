package com.mscompra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscompra.ContainersEnvironment;
import com.mscompra.DadosMok;
import com.mscompra.IntegrationTest;
import com.mscompra.model.Pedido;
import com.mscompra.service.PedidoService;
import com.mscompra.service.event.ProducerOrder;
import com.mscompra.service.exception.EntidadeNaoEncontradaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
public class PedidoControllerTest extends ContainersEnvironment {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProducerOrder producerOrder;

    private static final String ROTA_PEDIDO = "/pedido";

    private DadosMok dadosMok = new DadosMok();

    @DisplayName("POST - Must save successfully a new Order")
    @Test
    void deveCadastrarPedidoComSucesso() throws Exception {
        var payload = dadosMok.getPedido();
        var id = 1L;

        Mockito.doNothing().when(producerOrder).sendOrder(Mockito.any(Pedido.class));

        mockMvc.perform(post(ROTA_PEDIDO)
                .content(mapper.writeValueAsString(payload))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Pedido order = pedidoService.buscarOuFalharPorId(id);

        assertEquals(order.getId(), id);
        assertNotNull(order);
    }

    @DisplayName("GET - Must get successfully existent Order")
    @Test
    void deveBuscarPedidoComSucesso() throws Exception {
        deveCadastrarPedidoComSucesso();

        var id = 1L;

        mockMvc.perform(get(ROTA_PEDIDO.concat("/" + id)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("GET - Must fail when trying get a Order that does not exist")
    @Test
    void deveFalharAoBuscarPedidoQueNaoExiste() throws Exception {
        var id = 2L;

        mockMvc.perform(get(ROTA_PEDIDO.concat("/" + id)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("DELETE - Must fail when trying to delete order that does not exist")
    @Test
    void deveExcluirUmPedidoComSucesso() throws Exception {
        var id = 1L;

        mockMvc.perform(delete(ROTA_PEDIDO.concat("/" + id)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Throwable exception = assertThrows(EntidadeNaoEncontradaException.class, () -> pedidoService.excluir(id));

        assertEquals("Order id: " + id + " does not exist in database!", exception.getMessage());
    }
}
