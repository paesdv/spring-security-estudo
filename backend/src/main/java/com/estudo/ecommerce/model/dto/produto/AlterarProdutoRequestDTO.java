package com.estudo.ecommerce.model.dto.produto;

import java.math.BigDecimal;
import java.util.Optional;

public record AlterarProdutoRequestDTO (Optional<String> nome, Optional<String> descricao, Optional<BigDecimal> preco, Optional<Integer> estoque){
}
