package br.com.techtaste.ms_pedidos.dto;

import br.com.techtaste.ms_pedidos.model.ItemPedido;
import br.com.techtaste.ms_pedidos.model.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PedidoResponseDto(UUID id,
                                Status status,
                                String cpf,
                                List<ItemPedido> itens,
                                BigDecimal valorTotal,
                                LocalDate data) {
}
