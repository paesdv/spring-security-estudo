package com.estudo.ecommerce.service;

import com.estudo.ecommerce.exceptions.BusinessException;
import com.estudo.ecommerce.exceptions.ResourceNotFoundException;
import com.estudo.ecommerce.exceptions.UnauthorizedException;
import com.estudo.ecommerce.model.dto.pedido.PedidoResponseDTO;
import com.estudo.ecommerce.model.entity.Pedido;
import com.estudo.ecommerce.model.entity.User;
import com.estudo.ecommerce.model.enums.StatusPedido;
import com.estudo.ecommerce.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public Page<PedidoResponseDTO> listarPedidos(Pageable pageable) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return pedidoRepository.findByUsuario(user, pageable)
                .map(PedidoResponseDTO::toResponse);
    }

    public PedidoResponseDTO buscarPorId(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        validarPropriedade(pedido);

        return PedidoResponseDTO.toResponse(pedido);
    }

    public PedidoResponseDTO cancelarPedido(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        validarPropriedade(pedido);

        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new BusinessException("Pedido não pode mais ser cancelado.");
        }

        pedido.setStatus(StatusPedido.CANCELADO);

        return PedidoResponseDTO.toResponse(pedidoRepository.save(pedido));
    }

    public PedidoResponseDTO atualizarStatus(UUID id, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        pedido.setStatus(status);

        return PedidoResponseDTO.toResponse(pedidoRepository.save(pedido));
    }

    private void validarPropriedade(Pedido pedido) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (!pedido.getUsuario().getId().equals(user.getId()) && !isAdmin) {
            throw new UnauthorizedException("Você não tem permissão para acessar este pedido.");
        }
    }
}