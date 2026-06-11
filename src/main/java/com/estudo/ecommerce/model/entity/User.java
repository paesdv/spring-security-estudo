package com.estudo.ecommerce.model.entity;

import com.estudo.ecommerce.model.enums.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usuarios")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos;

    private LocalDate dataCadastro;

    public User atualizarSenha(String novaSenha){
        this.senha = novaSenha;
        return this;
    }
    public User atualizarEmail(String novoEmail){
        this.email = novoEmail;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() { return senha; }

    @Override
    public String getUsername() { return email; }

   
    @Override public boolean isEnabled()                  { return true; }
    @Override public boolean isAccountNonExpired()        { return true; }
    @Override public boolean isAccountNonLocked()         { return true; }
    @Override public boolean isCredentialsNonExpired()    { return true; }


}
