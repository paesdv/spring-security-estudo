package com.estudo.ecommerce.model.dto.user;

import com.estudo.ecommerce.model.entity.User;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponse(UUID id, String nome, String email, LocalDate dataCadastro) {

    public static UserResponse toResponse(User usuario) {
        return new UserResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataCadastro()
        );
    }

}
