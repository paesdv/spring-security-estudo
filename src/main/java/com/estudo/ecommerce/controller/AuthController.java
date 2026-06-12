package com.estudo.ecommerce.controller;

import com.estudo.ecommerce.model.dto.user.LoginRequestDTO;
import com.estudo.ecommerce.model.dto.user.RegisterRequestDTO;
import com.estudo.ecommerce.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public void register(@RequestBody @Valid RegisterRequestDTO dto) {
        authService.registerUser(dto);
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid LoginRequestDTO dto) {
        authService.login(dto);
    }

}
