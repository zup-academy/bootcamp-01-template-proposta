package br.com.proposta;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.proposta.dto.PropostaDTO;
import br.com.proposta.validator.ValidadorDocumento;

public class NovaPropostaTest {

	@Test
	@DisplayName("não deve aceitar quando não é cpf ou cnpj")
	void teste1() throws Exception {
		ValidadorDocumento validador = new ValidadorDocumento();
		boolean valido = validador.isValid("", null);
		Assertions.assertFalse(valido);
	}
	
	@Test
	@DisplayName("DTO nao deve ser nulo")
	void teste2()throws Exception {
		BigDecimal bd = new BigDecimal("10000.00");
		PropostaDTO propostadto = new PropostaDTO("teste@gmail.com", "teste", "teste endereco", bd, "990.952.230-60");
		assertNotNull(propostadto);
	}

}
