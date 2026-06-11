package com.estudo.ecommerce.repository;

import com.estudo.ecommerce.model.entity.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, UUID> {
}
