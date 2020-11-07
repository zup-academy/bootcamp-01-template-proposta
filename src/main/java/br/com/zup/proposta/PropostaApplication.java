package br.com.zup.proposta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication/*(exclude = SecurityAutoConfiguration.class)*/
@EnableAsync
@EnableScheduling
@EnableFeignClients
public class PropostaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropostaApplication.class, args);
	}

}
