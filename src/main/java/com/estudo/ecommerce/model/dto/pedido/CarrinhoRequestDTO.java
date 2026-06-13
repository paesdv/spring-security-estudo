package com.estudo.ecommerce.model.dto.pedido;

import java.util.UUID;

public record CarrinhoRequestDTO(UUID produtoId, Integer quantidade) {
}
