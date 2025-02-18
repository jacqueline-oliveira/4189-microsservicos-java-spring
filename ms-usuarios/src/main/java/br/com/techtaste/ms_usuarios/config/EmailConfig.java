package br.com.techtaste.ms_usuarios.config;

import br.com.techtaste.ms_usuarios.dto.EmailDto;
import br.com.techtaste.ms_usuarios.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;


@Configuration
public class EmailConfig {
    @Autowired
    private UsuarioService service;

    @Value("fila.mensagem.usuario")
    private String queue;

    @Bean
    public Queue queue(){
        return new Queue(queue, true);
    }

    @RabbitListener(queues = "fila.mensagem.usuario")
    private void enviaEmail(@Payload EmailDto mensagem){
        System.out.println(mensagem);
        service.enviarMensagem(mensagem);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }


}
