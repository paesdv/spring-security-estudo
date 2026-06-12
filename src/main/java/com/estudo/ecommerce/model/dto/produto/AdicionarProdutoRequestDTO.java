package com.estudo.ecommerce.model.dto.produto;

import java.math.BigDecimal;

public record AdicionarProdutoRequestDTO(String nome, String descricao, BigDecimal preco) {
}
