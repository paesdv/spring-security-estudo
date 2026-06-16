package com.estudo.ecommerce.controller;

import com.estudo.ecommerce.model.dto.user.AlterarEmailRequest;
import com.estudo.ecommerce.model.dto.user.AlterarSenhaRequest;
import com.estudo.ecommerce.model.dto.user.RegisterRequestDTO;
import com.estudo.ecommerce.model.dto.user.UserResponse;
import com.estudo.ecommerce.model.entity.User;
import com.estudo.ecommerce.repository.UserRepository;
import com.estudo.ecommerce.service.AuthService;
import com.estudo.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usuario")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/troca-email")
    public ResponseEntity<?> trocarEmail(@RequestBody AlterarEmailRequest request, Authentication auth){

        String emailAtual = auth.getName();
        userService.changeEmail(emailAtual, request);
        return ResponseEntity.ok("Email atualizado com sucesso!");
    }

    @PostMapping("/troca-senha")
    public ResponseEntity<?> trocarSenha(@RequestBody AlterarSenhaRequest request, Authentication auth){

        String emailAtual = auth.getName();
        userService.changePassword(emailAtual, request);
        return ResponseEntity.ok("Senha atualizada com sucesso!");
    }

    @GetMapping
    public ResponseEntity<UserResponse> getByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .map(UserResponse::toResponse)
                .<ResponseEntity<UserResponse>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
