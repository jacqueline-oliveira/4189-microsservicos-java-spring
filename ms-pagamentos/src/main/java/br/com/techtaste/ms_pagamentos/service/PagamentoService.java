package br.com.techtaste.ms_pagamentos.service;

import br.com.techtaste.ms_pagamentos.dto.AutorizacaoDto;
import br.com.techtaste.ms_pagamentos.model.GeradorAutorizacao;
import br.com.techtaste.ms_pagamentos.model.Pagamento;
import br.com.techtaste.ms_pagamentos.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository repository;

    public AutorizacaoDto autorizarPagamento(String id) {
        Pagamento pagamento = new Pagamento();
        pagamento.setIdPedido(id);
        String status = GeradorAutorizacao.getRandomBoolean() ? "Autorizado" : "Recusado";
        pagamento.setStatus(status);
        repository.save(pagamento);
        return new AutorizacaoDto(pagamento.getIdPedido(), pagamento.getStatus());
    }

    public List<AutorizacaoDto> obterTodos() {
        return repository.findAll().stream()
                .map(a -> new AutorizacaoDto(a.getIdPedido(), a.getStatus()))
                .collect(Collectors.toList());
    }
}
