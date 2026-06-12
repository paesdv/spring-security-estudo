package com.estudo.ecommerce.service;

import com.estudo.ecommerce.model.dto.user.RegisterRequestDTO;
import com.estudo.ecommerce.model.entity.User;
import com.estudo.ecommerce.model.enums.Roles;
import com.estudo.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterRequestDTO dto) throws RuntimeException {

        if(userRepository.findByEmail(dto.email()).isPresent()){
            throw new RuntimeException("Email já cadastrado");
        }

        userRepository.save(User.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(passwordEncoder.encode(dto.senha()))
                .role(Roles.CUSTOMER)
                .dataCadastro(LocalDate.now())
                .build()
        );

    }

}
