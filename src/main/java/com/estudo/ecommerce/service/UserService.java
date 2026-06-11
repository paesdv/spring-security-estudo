package com.estudo.ecommerce.service;

import com.estudo.ecommerce.model.dto.user.AlterarEmailRequest;
import com.estudo.ecommerce.model.dto.user.AlterarSenhaRequest;
import com.estudo.ecommerce.model.dto.user.UserRequest;
import com.estudo.ecommerce.model.dto.user.UserResponse;
import com.estudo.ecommerce.model.entity.User;
import com.estudo.ecommerce.model.enums.Roles;
import com.estudo.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse registerUser(UserRequest dto) {
       return UserResponse.toResponse(
               userRepository.save(
                       User.builder()
                               .nome(dto.nome())
                               .senha(dto.senha())
                               .email(dto.email())
                               .role(Roles.CUSTOMER)
                               .build()
               )
       );
    }

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

    public UserResponse changeEmail(AlterarEmailRequest dto) {
        return UserResponse.toResponse(
                userRepository.save(
                        userRepository.findByEmail(dto.email())
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"))
                                .atualizarSenha(dto.novoEmail())
                )
        );
    }



}
