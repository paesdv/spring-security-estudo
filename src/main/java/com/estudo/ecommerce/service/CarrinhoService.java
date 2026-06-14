package com.estudo.ecommerce.service;

import com.estudo.ecommerce.exceptions.BusinessException;
import com.estudo.ecommerce.exceptions.ResourceNotFoundException;
import com.estudo.ecommerce.model.dto.pedido.CarrinhoRequestDTO;
import com.estudo.ecommerce.model.dto.pedido.CarrinhoResponseDTO;
import com.estudo.ecommerce.model.dto.pedido.PedidoResponseDTO;
import com.estudo.ecommerce.model.entity.Pedido;
import com.estudo.ecommerce.model.entity.PedidoItem;
import com.estudo.ecommerce.model.entity.Produto;
import com.estudo.ecommerce.model.entity.User;
import com.estudo.ecommerce.model.enums.StatusPedido;
import com.estudo.ecommerce.repository.PedidoRepository;
import com.estudo.ecommerce.repository.ProdutoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Object> hashOps;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    @PostConstruct
    public void init() {
        this.hashOps = redisTemplate.opsForHash();
    }

    public CarrinhoResponseDTO addProduto(CarrinhoRequestDTO request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usuarioId = "carrinho: " + user.getId().toString();

        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        BigDecimal valorTotal = produto.getPreco().multiply(new BigDecimal(request.quantidade()));

        CarrinhoResponseDTO item = new CarrinhoResponseDTO(
                produto.getNome(),
                request.quantidade(),
                valorTotal
        );

        hashOps.put(usuarioId, request.produtoId().toString(), item);

        return item;
    }

    public void deletarProduto(UUID id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String chave = "carrinho: " + user.getId().toString();

        if(!hashOps.hasKey(chave, id.toString())) {
            throw new ResourceNotFoundException("Produto não encontrado no carrinho.");
        }
        hashOps.delete(chave, id.toString());
    }

    public List<CarrinhoResponseDTO> listarCarrinho() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String chave = "carrinho: " + user.getId().toString();

        return hashOps.values(chave)
                .stream()
                .map(item -> (CarrinhoResponseDTO) item)
                .collect(Collectors.toList());
    }

    public void limparCarrinho(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String chave = "carrinho: " + user.getId().toString();

        redisTemplate.delete(chave);
    }

    public CarrinhoResponseDTO atualizarQuantidade(UUID id, Integer quantidade) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String chave = "carrinho: " + user.getId().toString();

        if(!hashOps.hasKey(chave, id.toString())) {
            throw new ResourceNotFoundException("Produto não encontrado no carrinho.");
        }

        CarrinhoResponseDTO item = (CarrinhoResponseDTO) hashOps.get(chave, id.toString());

        BigDecimal precoUnitario = item.valorTotal().divide(BigDecimal.valueOf(item.quantidade()));
        BigDecimal novoValorTotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));

        CarrinhoResponseDTO atualizado = new CarrinhoResponseDTO(
                item.produtoNome(),
                quantidade,
                novoValorTotal
        );

        hashOps.put(chave, id.toString(), atualizado);
        return atualizado;

    }

    public PedidoResponseDTO fecharPedido() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String chave = "carrinho: " + user.getId().toString();

        Map<String, Object> entradas = hashOps.entries(chave);

        if (entradas.isEmpty()) {
            throw new BusinessException("Carrinho vazio.");
        }

        Pedido pedido = Pedido.builder()
                .usuario(user)
                .status(StatusPedido.PENDENTE)
                .build();

        List<PedidoItem> itensPedido = new ArrayList<>();

        entradas.forEach((produtoIdStr, valor) -> {
            CarrinhoResponseDTO item = (CarrinhoResponseDTO) valor;
            UUID produtoId = UUID.fromString(produtoIdStr);

            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

            if (produto.getEstoque() < item.quantidade()) {
                throw new BusinessException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            produto.setEstoque(produto.getEstoque() - item.quantidade());
            produtoRepository.save(produto);

            BigDecimal precoUnitario = item.valorTotal().divide(BigDecimal.valueOf(item.quantidade()));

            PedidoItem pedidoItem = PedidoItem.builder()
                    .quantidade(item.quantidade())
                    .precoUnitario(precoUnitario)
                    .pedido(pedido)
                    .produto(produto)
                    .build();

            itensPedido.add(pedidoItem);
        });

        pedido.setItens(itensPedido);
        pedido.setValorTotal(pedido.calcularTotal());

        pedidoRepository.save(pedido);

        redisTemplate.delete(chave);

        return PedidoResponseDTO.toResponse(pedido);
    }

}