package com.estudo.ecommerce.model.dto.user;

public record TokenResponseDTO(String token, long expiresIn) {
}
