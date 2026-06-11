package com.estudo.ecommerce.model.dto.user;

public record AlterarSenhaRequest(String email, String senha, String novaSenha) {
}
