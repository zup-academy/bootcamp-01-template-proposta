package br.com.zup.nossocartao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class NossoCartaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NossoCartaoApplication.class, args);
	}

}
