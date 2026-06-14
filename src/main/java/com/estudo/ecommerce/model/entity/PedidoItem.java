package com.estudo.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pedido_item")
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private Integer quantidade;

    private BigDecimal precoUnitario; // preço na hora da compra, congelado

    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Produto produto;

}
