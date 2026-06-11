package com.estudo.ecommerce.repository;

import com.estudo.ecommerce.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
}
