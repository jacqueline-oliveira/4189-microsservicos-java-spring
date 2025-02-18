package br.com.techtaste.ms_usuarios.controller;

import br.com.techtaste.ms_usuarios.dto.UsuarioDto;
import br.com.techtaste.ms_usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<UsuarioDto> obterTodos() {
        return service.obterTodos();
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> cadastrarUsuario(@RequestBody UsuarioDto usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrarUsuario(usuario));
    }

}
