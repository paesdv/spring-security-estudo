package com.estudo.ecommerce.controller;

import com.estudo.ecommerce.model.dto.user.LoginRequestDTO;
import com.estudo.ecommerce.model.dto.user.RegisterRequestDTO;
import com.estudo.ecommerce.model.dto.user.TokenResponseDTO;
import com.estudo.ecommerce.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO dto)throws Exception {
        authService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado!");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) throws Exception {
        TokenResponseDTO token = authService.login(dto);
        return ResponseEntity.ok(token);
    }

}
