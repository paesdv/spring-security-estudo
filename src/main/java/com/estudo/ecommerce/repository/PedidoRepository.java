package com.estudo.ecommerce.repository;

import com.estudo.ecommerce.model.entity.Pedido;
import com.estudo.ecommerce.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    Page<Pedido> findByUsuario(User usuario, Pageable pageable);
}
