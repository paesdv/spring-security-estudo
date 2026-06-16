package com.estudo.ecommerce.controller;

import com.estudo.ecommerce.model.dto.produto.AdicionarProdutoRequestDTO;
import com.estudo.ecommerce.model.dto.produto.AlterarProdutoRequestDTO;
import com.estudo.ecommerce.model.dto.produto.ProdutoResponseDTO;
import com.estudo.ecommerce.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/v1/produto")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> listarProdutos(
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(produtoService.listarProdutos(pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<ProdutoResponseDTO>> listarPorNome(
            @RequestParam String nome,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(
                produtoService.listarPorNome(nome, pageable)
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProdutoResponseDTO> createProduct(@RequestBody @Valid AdicionarProdutoRequestDTO request){
        ProdutoResponseDTO produto = produtoService.createProduct(request);

        URI uri = URI.create("/v1/produto/" + produto.id());
        return ResponseEntity.created(uri).body(produto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProdutoResponseDTO> updateProduct(@PathVariable UUID id, @RequestBody @Valid AlterarProdutoRequestDTO request){
        return ResponseEntity.ok(produtoService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id){
        produtoService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


}
