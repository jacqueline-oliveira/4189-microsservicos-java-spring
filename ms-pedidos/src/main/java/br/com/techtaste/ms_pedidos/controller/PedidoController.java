package br.com.techtaste.ms_pedidos.controller;

import br.com.techtaste.ms_pedidos.dto.PedidoRequestDto;
import br.com.techtaste.ms_pedidos.dto.PedidoResponseDto;
import br.com.techtaste.ms_pedidos.service.PedidoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService service;

    @PostMapping
    @CircuitBreaker(name = "verificaAutorizacao", fallbackMethod = "erroAoCadastrarPedido")
    public ResponseEntity<PedidoResponseDto> cadastrarPedido(@RequestBody PedidoRequestDto pedidoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrarPedido(pedidoDto, false));
    }

    public ResponseEntity<PedidoResponseDto> erroAoCadastrarPedido(@RequestBody @Valid PedidoRequestDto pedidoDto, Exception e) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrarPedido(pedidoDto, true));
    }

    @GetMapping
    public List<PedidoResponseDto> obterTodos() {
        return service.obterTodos();
    }

    @GetMapping("/response")
    public String obterPorta(@Value("${local.server.port}") String porta) {
        return String.format("Resposta vinda da porta %s", porta);

    }
}
