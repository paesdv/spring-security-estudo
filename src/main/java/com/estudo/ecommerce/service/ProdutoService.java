package com.estudo.ecommerce.service;

import com.estudo.ecommerce.exceptions.BusinessException;
import com.estudo.ecommerce.exceptions.ResourceNotFoundException;
import com.estudo.ecommerce.model.dto.produto.AdicionarProdutoRequestDTO;
import com.estudo.ecommerce.model.dto.produto.AlterarProdutoRequestDTO;
import com.estudo.ecommerce.model.dto.produto.ProdutoResponseDTO;
import com.estudo.ecommerce.model.entity.Produto;
import com.estudo.ecommerce.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Page<ProdutoResponseDTO> listarProdutos(Pageable pageable) {
        return produtoRepository.findAll(pageable)
                .map(ProdutoResponseDTO::toResponse);
    }

    public Page<ProdutoResponseDTO> listarPorNome(String nome, Pageable pageable) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(ProdutoResponseDTO::toResponse);
    }

    public ProdutoResponseDTO createProduct(AdicionarProdutoRequestDTO request) {
        if(produtoRepository.findByNome(request.nome()).isPresent()){
            throw new BusinessException("Produto já cadastrado");
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
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        produto.atualizarDados(request);

        return ProdutoResponseDTO.toResponse(produtoRepository.save(produto));
    }

    public void deleteProduct(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        produtoRepository.delete(produto);
    }

}