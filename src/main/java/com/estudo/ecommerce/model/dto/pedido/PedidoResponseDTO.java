package com.estudo.ecommerce.model.dto.pedido;

import com.estudo.ecommerce.model.entity.Pedido;
import com.estudo.ecommerce.model.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PedidoResponseDTO(
        UUID id,
        LocalDate dataPedido,
        StatusPedido status,
        BigDecimal valorTotal,
        List<PedidoItemResponseDTO> itens
) {

    public static PedidoResponseDTO toResponse(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getStatus(),
                pedido.getValorTotal(),
                pedido.getItens().stream()
                        .map(PedidoItemResponseDTO::toResponse)
                        .toList()
        );
    }
}