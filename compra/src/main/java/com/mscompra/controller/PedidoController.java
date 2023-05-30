package com.mscompra.controller;

import com.mscompra.model.Pedido;
import com.mscompra.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> save(@RequestBody @Valid Pedido pedido) {
        log.info("received order: {}", pedido.getProduto());
        return ResponseEntity.ok(pedidoService.salvar(pedido));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarOuFalharPorId(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        pedidoService.excluir(id);
    }

}
