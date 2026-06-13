package com.estudo.ecommerce.service;

import com.estudo.ecommerce.model.dto.pedido.CarrinhoRequestDTO;
import com.estudo.ecommerce.model.dto.pedido.CarrinhoResponseDTO;
import com.estudo.ecommerce.model.entity.Produto;
import com.estudo.ecommerce.model.entity.User;
import com.estudo.ecommerce.repository.ProdutoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Object> hashOps;
    private final ProdutoRepository produtoRepository;

    @PostConstruct
    public void init() {
        this.hashOps = redisTemplate.opsForHash();
    }

    public CarrinhoResponseDTO addProduto(UUID id, CarrinhoRequestDTO request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usuarioId = "Carrinho: " + user.getId().toString();

        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        CarrinhoResponseDTO item = new CarrinhoResponseDTO(
                produto.getNome(),
                produto.getEstoque(),
                produto.getPreco()
        );

        hashOps.put(usuarioId, request.produtoId().toString(), item);

        return item;
    }

}
