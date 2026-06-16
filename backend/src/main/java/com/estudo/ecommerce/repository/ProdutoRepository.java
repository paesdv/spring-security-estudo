package com.estudo.ecommerce.repository;

import com.estudo.ecommerce.model.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    Optional<Produto> findByNome(String nome);
    Page<Produto> findAll(Pageable pageable);
    Page<Produto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
