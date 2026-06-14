package com.estudo.ecommerce.model.dto.pedido;

import com.estudo.ecommerce.model.entity.PedidoItem;
import com.estudo.ecommerce.model.entity.User;
import com.estudo.ecommerce.model.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PedidoResponseDTO(UUID id,
                                User usuario,
                                List<PedidoItem> pedidoItems,
                                StatusPedido status,
                                BigDecimal valorTotal,
                                LocalDate dataPedido) {
}
