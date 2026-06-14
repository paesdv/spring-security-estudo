package com.estudo.ecommerce.controller;

import com.estudo.ecommerce.model.dto.pedido.CarrinhoRequestDTO;
import com.estudo.ecommerce.model.dto.pedido.CarrinhoResponseDTO;
import com.estudo.ecommerce.model.entity.Pedido;
import com.estudo.ecommerce.service.CarrinhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/carrinho")
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @PostMapping
    public ResponseEntity<CarrinhoResponseDTO> addProduto(@RequestBody @Valid CarrinhoRequestDTO request) {
        return ResponseEntity.ok(carrinhoService.addProduto(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarrinhoResponseDTO> atualizarQuantidade(
            @PathVariable UUID id,
            @RequestParam Integer quantidade) {
        return ResponseEntity.ok(carrinhoService.atualizarQuantidade(id, quantidade));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable UUID id) {
        carrinhoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CarrinhoResponseDTO>> listarCarrinho() {
        return ResponseEntity.ok(carrinhoService.listarCarrinho());
    }

    @DeleteMapping
    public ResponseEntity<Void> limparCarrinho() {
        carrinhoService.limparCarrinho();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/fechar")
    public ResponseEntity<Pedido> fecharPedido() {
        return ResponseEntity.ok(carrinhoService.fecharPedido());
    }
}