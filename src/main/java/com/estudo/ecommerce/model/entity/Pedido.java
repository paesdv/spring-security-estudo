package com.estudo.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pedido")
public class Pedido {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome_pedido")
    private String nomePedido;

    @Column(name = "descricao_pedido")
    private String descricaoPedido;

    @Column(name = "preco")
    private BigDecimal precoPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User usuario;

}
