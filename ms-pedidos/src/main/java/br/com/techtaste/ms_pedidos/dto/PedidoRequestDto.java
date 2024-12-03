package br.com.techtaste.ms_pedidos.dto;

import br.com.techtaste.ms_pedidos.model.ItemPedido;

import java.util.List;

public record PedidoRequestDto(String cpf,
                               List<ItemPedido> itens) {
}
