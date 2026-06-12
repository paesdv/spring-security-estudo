package com.estudo.ecommerce.service;

import com.estudo.ecommerce.model.dto.user.AlterarEmailRequest;
import com.estudo.ecommerce.model.dto.user.AlterarSenhaRequest;
import com.estudo.ecommerce.model.dto.user.UserRequest;
import com.estudo.ecommerce.model.dto.user.UserResponse;
import com.estudo.ecommerce.model.entity.User;
import com.estudo.ecommerce.model.enums.Roles;
import com.estudo.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserResponse changePassword(AlterarSenhaRequest dto) {
        return UserResponse.toResponse(
                userRepository.save(
                        userRepository.findByEmail(dto.email())
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"))
                                .atualizarSenha(dto.senha())
                )
        );
    }

    @Transactional
    public void changeEmail(String emailAtual, AlterarEmailRequest dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(emailAtual, dto.senha())
        );

        User user = userRepository.findByEmail(emailAtual)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if(userRepository.findByEmail(dto.novoEmail()).isPresent()) {
            throw new RuntimeException("Email solicitado já em uso.");
        }

        user.setEmail(dto.novoEmail());
        userRepository.save(user);

    }



}
