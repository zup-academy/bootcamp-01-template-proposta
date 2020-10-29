package br.com.zup.cartaoproposta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CartaoPropostaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartaoPropostaApplication.class, args);
	}

}
