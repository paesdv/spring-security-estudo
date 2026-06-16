package com.estudo.ecommerce.model.dto.produto;

import com.estudo.ecommerce.model.dto.user.UserResponse;
import com.estudo.ecommerce.model.entity.Produto;
import com.estudo.ecommerce.model.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ProdutoResponseDTO(UUID id, String nome, String descricao, BigDecimal preco, Integer estoque, LocalDate dataCadastro) {

    public static ProdutoResponseDTO toResponse(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getDataCadastro()
        );
    }
}
