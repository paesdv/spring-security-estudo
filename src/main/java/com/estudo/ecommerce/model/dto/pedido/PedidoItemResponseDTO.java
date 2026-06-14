package com.estudo.ecommerce.model.dto.pedido;

import com.estudo.ecommerce.model.entity.PedidoItem;

import java.math.BigDecimal;

public record PedidoItemResponseDTO(String produtoNome, Integer quantidade, BigDecimal precoUnitario) {

    public static PedidoItemResponseDTO toResponse(PedidoItem item) {
        return new PedidoItemResponseDTO(
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario()
        );
    }
}
