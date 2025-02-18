package br.com.techtaste.ms_usuarios.service;

import br.com.techtaste.ms_usuarios.dto.EmailDto;
import br.com.techtaste.ms_usuarios.dto.UsuarioDto;
import br.com.techtaste.ms_usuarios.model.Usuario;
import br.com.techtaste.ms_usuarios.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private JavaMailSender sender;

    public List<UsuarioDto> obterTodos() {
        return repository.findAll().stream()
                .map(u -> new UsuarioDto(u.getId(), u.getCpf(), u.getNome(),
                        u.getEmail()))
                .collect(Collectors.toList());
    }


    public UsuarioDto cadastrarUsuario(UsuarioDto usuario) {
        Usuario usuarioEntity = new Usuario();
        BeanUtils.copyProperties(usuario, usuarioEntity);
        repository.save(usuarioEntity);
        return new UsuarioDto(usuarioEntity.getId(), usuarioEntity.getCpf(),
                usuarioEntity.getNome(), usuarioEntity.getEmail());
    }

    public void enviarMensagem(EmailDto mensagem) {
        Optional<Usuario> usuario = repository.findByCpf(mensagem.cpf());

        if (usuario.isPresent()) {
            SimpleMailMessage enviaMensagem = new SimpleMailMessage();

            enviaMensagem.setFrom("SEU-EMAIL@gmail.com");
            enviaMensagem.setTo(usuario.get().getEmail());
            enviaMensagem.setSubject("Status do pedido " + mensagem.pedidoId());
            enviaMensagem.setText("O pedido está: " + mensagem.status());
        try {
            sender.send(enviaMensagem);
            System.out.println("Mensagem enviada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao enviar mensagem!");
        }
        }
    }
}
