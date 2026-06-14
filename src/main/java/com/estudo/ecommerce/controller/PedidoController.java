package com.estudo.ecommerce.controller;

import com.estudo.ecommerce.model.dto.pedido.PedidoResponseDTO;
import com.estudo.ecommerce.model.enums.StatusPedido;
import com.estudo.ecommerce.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<Page<PedidoResponseDTO>> listarPedidos(Pageable pageable) {
        return ResponseEntity.ok(pedidoService.listarPedidos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponseDTO> cancelarPedido(@PathVariable UUID id) {
        return ResponseEntity.ok(pedidoService.cancelarPedido(id));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(
            @PathVariable UUID id,
            @RequestParam StatusPedido status) {
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, status));
    }
}