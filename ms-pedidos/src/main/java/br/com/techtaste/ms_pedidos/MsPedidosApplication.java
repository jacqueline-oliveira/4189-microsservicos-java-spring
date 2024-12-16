package br.com.techtaste.ms_pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPedidosApplication.class, args);
	}

}
