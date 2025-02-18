package br.com.techtaste.ms_usuarios.dto;

public record UsuarioDto(Long id,
                         String cpf,
                         String nome,
                         String email)  {
}
