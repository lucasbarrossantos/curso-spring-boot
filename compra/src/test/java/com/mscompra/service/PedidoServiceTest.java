package com.mscompra.service;

import com.mscompra.DadosMok;
import com.mscompra.model.Pedido;
import com.mscompra.repository.PedidoRepository;
import com.mscompra.service.event.ProducerOrder;
import com.mscompra.service.exception.NegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProducerOrder producerOrder;

    private DadosMok mock = new DadosMok();

    @DisplayName("Salvar pedido com sucesso")
    @Test
    void deveSalvarUmPedidoComSucesso() {
        var pedidoMok = mock.getPedido();

        Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class))).thenReturn(pedidoMok);
        Mockito.doNothing().when(producerOrder).sendOrder(Mockito.any(Pedido.class));

        var pedidoSalvo = pedidoService.salvar(pedidoMok);

        assertEquals(pedidoMok.getCep(), pedidoSalvo.getCep());
        assertNotNull(pedidoSalvo.getCep());
    }

    @DisplayName("Deve falhar na busca de pedido que nao existe")
    @Test
    void deveFalharNaBuscaDePedidoNaoExistente() {
        var id = 1L;

        Throwable exception = assertThrows(NegocioException.class,
                () -> pedidoService.buscarOuFalharPorId(id));

        assertEquals("Order id: "+ id +" does not exist in database!", exception.getMessage());
    }

    @DisplayName("Deve buscar um pedido com sucesso na base de dados")
    @Test
    void deveBuscarPedidoComSucesso() {
        var pedidoMok = mock.getPedidoSalvo();
        var id = 1L;

        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedidoMok));

        var pedidoSalvo = pedidoService.buscarOuFalharPorId(id);

        assertEquals(pedidoMok.getId(), pedidoSalvo.getId());
        assertNotNull(pedidoSalvo);
        Mockito.verify(pedidoRepository, Mockito.atLeastOnce()).findById(id);
    }

    @DisplayName("Deve excluir o pedido com sucesso")
    @Test
    void deveExcluirPedidoComSucesso() {
        var pedidoMok = mock.getPedidoSalvo();
        var id = 1L;

        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedidoMok));
        Mockito.doNothing().when(pedidoRepository).deleteById(id);

        pedidoService.excluir(id);
        Mockito.verify(pedidoRepository, Mockito.atLeastOnce()).deleteById(id);
    }

    @DisplayName("Deve falhar ao excluir o pedido que nao existe")
    @Test
    void deveFalharAoEXcluirPedidoNaoExistente() {
        var id = 1L;

        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(NegocioException.class, () -> pedidoService.excluir(id));

        assertEquals("Order id: "+ id +" does not exist in database!", exception.getMessage());
    }

}
