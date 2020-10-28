package br.com.cartao.proposta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PropostaCartaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropostaCartaoApplication.class, args);
	}

}
