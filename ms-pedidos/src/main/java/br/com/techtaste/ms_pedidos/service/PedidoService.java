package br.com.techtaste.ms_pedidos.service;

import br.com.techtaste.ms_pedidos.dto.AutorizacaoDto;
import br.com.techtaste.ms_pedidos.dto.EmailDto;
import br.com.techtaste.ms_pedidos.dto.PedidoRequestDto;
import br.com.techtaste.ms_pedidos.dto.PedidoResponseDto;
import br.com.techtaste.ms_pedidos.model.Pedido;
import br.com.techtaste.ms_pedidos.model.Status;
import br.com.techtaste.ms_pedidos.producer.UsuarioProducer;
import br.com.techtaste.ms_pedidos.repository.PedidoRepository;
import br.com.techtaste.ms_pedidos.utils.AutorizacaoPagamentoClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    private final PedidoRepository repository;
    private final AutorizacaoPagamentoClient client;
    private final UsuarioProducer producer;

    public PedidoService(PedidoRepository repository, AutorizacaoPagamentoClient client, UsuarioProducer producer) {
        this.repository = repository;
        this.client = client;
        this.producer = producer;
    }

    public PedidoResponseDto cadastrarPedido(PedidoRequestDto pedidoDto, Boolean comErro) {
        Pedido pedido = new Pedido();
        BeanUtils.copyProperties(pedidoDto, pedido);
        Status status = Status.AGUARDANDO_PAGAMENTO;
        pedido.setStatus(status);
        pedido.setData(LocalDate.now());
        pedido.calcularTotal();
        repository.save(pedido);
        if (comErro) {
            status = Status.ERRO_CONSULTA_PGTO;
        } else {
            status = obterStatusPagamento(pedido.getId().toString());
        }
        pedido.setStatus(status);
        repository.save(pedido);
        producer.enviarEmail(new EmailDto(pedido.getCpf(), pedido.getId().toString(), pedido.getStatus().toString()));
        return new PedidoResponseDto(pedido.getId(), pedido.getStatus(),
                pedido.getCpf(), pedido.getItens(), pedido.getValorTotal(),
                pedido.getData());
    }

    private Status obterStatusPagamento(String id) {
        AutorizacaoDto autorizacao = client.obterAutorizacao(id);
        if (autorizacao.status().equalsIgnoreCase("autorizado")) {
            return Status.PREPARANDO;
        }

        return Status.RECUSADO;
    }

    public List<PedidoResponseDto> obterTodos() {
        return repository.findAll().stream()
                .map(pedido -> new PedidoResponseDto(pedido.getId(), pedido.getStatus(),
                        pedido.getCpf(), pedido.getItens(), pedido.getValorTotal(),
                        pedido.getData()))
                .collect(Collectors.toList());
    }
}
