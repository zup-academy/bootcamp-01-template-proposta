package br.com.proposta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PropostaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropostaApiApplication.class, args);
	}

}
