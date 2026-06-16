package com.estudo.ecommerce.service;

import com.estudo.ecommerce.exceptions.BusinessException;
import com.estudo.ecommerce.exceptions.ResourceNotFoundException;
import com.estudo.ecommerce.model.dto.user.AlterarEmailRequest;
import com.estudo.ecommerce.model.dto.user.AlterarSenhaRequest;
import com.estudo.ecommerce.model.entity.User;
import com.estudo.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final PasswordEncoder passwordEncoder;


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void changePassword(String emailAtual, AlterarSenhaRequest dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(emailAtual, dto.senha())
        );
        User user = userRepository.findByEmail(emailAtual)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if(dto.novaSenha().equals(dto.senha())) {
            throw new BusinessException("A nova senha tem que ser diferente da antiga");
        }
        user.setSenha(passwordEncoder.encode(dto.novaSenha()));
        userRepository.save(user);

    }

    @Transactional
    public void changeEmail(String emailAtual, AlterarEmailRequest dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(emailAtual, dto.senha())
        );

        User user = userRepository.findByEmail(emailAtual)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if(userRepository.findByEmail(dto.novoEmail()).isPresent()) {
            throw new BusinessException("Email solicitado já em uso.");
        }

        user.setEmail(dto.novoEmail());
        userRepository.save(user);

    }

}