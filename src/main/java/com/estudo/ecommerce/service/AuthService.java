package com.estudo.ecommerce.service;

import com.estudo.ecommerce.config.TokenProvider;
import com.estudo.ecommerce.model.dto.user.LoginRequestDTO;
import com.estudo.ecommerce.model.dto.user.RegisterRequestDTO;
import com.estudo.ecommerce.model.dto.user.TokenResponseDTO;
import com.estudo.ecommerce.model.entity.User;
import com.estudo.ecommerce.model.enums.Roles;
import com.estudo.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    @Value("${jwt.expiration}")
    private long expirationTime;

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

    public TokenResponseDTO login(LoginRequestDTO dto) throws RuntimeException {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.senha()));
            String token = tokenProvider.gerarToken(authentication);

            return new TokenResponseDTO(token, expirationTime);

        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Email ou senha incorretos");
        }catch(Exception e){
            throw e;
        }
    }

}
