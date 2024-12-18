package br.com.techtaste.ms_pagamentos.controller;

import br.com.techtaste.ms_pagamentos.dto.AutorizacaoDto;
import br.com.techtaste.ms_pagamentos.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    @Autowired
    private PagamentoService service;

    @GetMapping("/autorizacao/{id}")
    public AutorizacaoDto obterAutorizacaoPagamento(@PathVariable String id) {
        return service.autorizarPagamento(id);
    }

    @GetMapping
    public List<AutorizacaoDto> obterTodos() {
        return service.obterTodos();
    }
}
