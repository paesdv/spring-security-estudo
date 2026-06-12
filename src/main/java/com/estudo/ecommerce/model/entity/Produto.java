package com.estudo.ecommerce.model.entity;

import com.estudo.ecommerce.model.dto.produto.AlterarProdutoRequestDTO;
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
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal preco;

    private Integer estoque;

    @Column(nullable = false)
    private LocalDate dataCadastro;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = LocalDate.now();
    }



    public void atualizarDados(AlterarProdutoRequestDTO request) {
        request.nome().ifPresent(this::setNome);
        request.descricao().ifPresent(this::setDescricao);
        request.preco().ifPresent(this::setPreco);
        request.estoque().ifPresent(this::setEstoque);
    }

}
