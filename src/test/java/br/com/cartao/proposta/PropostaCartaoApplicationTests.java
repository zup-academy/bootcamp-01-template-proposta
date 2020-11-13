package br.com.cartao.proposta;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile(value = "test")
class PropostaCartaoApplicationTests {

	@Test
	void contextLoads() {
	}

}
