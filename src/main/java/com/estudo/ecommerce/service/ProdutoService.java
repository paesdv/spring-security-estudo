package com.estudo.ecommerce.service;


import com.estudo.ecommerce.model.dto.produto.AdicionarProdutoRequestDTO;
import com.estudo.ecommerce.model.dto.produto.AlterarProdutoRequestDTO;
import com.estudo.ecommerce.model.dto.produto.ProdutoResponseDTO;
import com.estudo.ecommerce.model.dto.user.AlterarSenhaRequest;
import com.estudo.ecommerce.model.dto.user.UserResponse;
import com.estudo.ecommerce.model.entity.Produto;
import com.estudo.ecommerce.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoResponseDTO createProduct(AdicionarProdutoRequestDTO request) {
        if(produtoRepository.findByNome(request.nome()).isPresent()){
            throw new RuntimeException("Produto já cadastrado");
        }

        Produto produto = Produto.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .preco(request.preco())
                .build();

        return ProdutoResponseDTO.toResponse(produtoRepository.save(produto));
    }

    public ProdutoResponseDTO updateProduct(UUID id, AlterarProdutoRequestDTO request) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        produto.atualizarDados(request);

        return ProdutoResponseDTO.toResponse(produtoRepository.save(produto));
    }

}
