package com.estudo.ecommerce.model.dto.pedido;

import java.math.BigDecimal;

public record CarrinhoResponseDTO(String produtoNome, Integer quantidade, BigDecimal valorTotal) {
}
