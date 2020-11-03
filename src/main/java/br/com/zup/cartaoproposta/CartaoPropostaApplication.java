package br.com.zup.cartaoproposta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class CartaoPropostaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartaoPropostaApplication.class, args);
	}

}
