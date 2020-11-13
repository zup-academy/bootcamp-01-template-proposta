package br.com.proposta;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.proposta.dto.PropostaDTO;
import br.com.proposta.model.Proposta;
import br.com.proposta.service.PropostaServices;
import br.com.proposta.validator.ValidadorDocumento;

public class NovaPropostaTest {

	private final PropostaServices propostaservices = Mockito.mock(PropostaServices.class);
	
	@Test
	@DisplayName("valida documento valido")
	void teste1() throws Exception {
		ValidadorDocumento validador = new ValidadorDocumento();
		boolean valido = validador.isValid("57120153072", null);
		Assertions.assertTrue(valido);
	}
	
	@Test
	@DisplayName("valida documento não valido")
	void teste2() throws Exception {
		ValidadorDocumento validador = new ValidadorDocumento();
		boolean valido = validador.isValid("", null);
		Assertions.assertFalse(valido);
	}
	
	@Test
	@DisplayName("criar um request valido e inserir no banco")
	void teste3()throws Exception {
		BigDecimal bd = new BigDecimal("10000.00");
		PropostaDTO propostadto = new PropostaDTO("teste@gmail.com", "teste", "teste endereco", bd, "990.952.230-60");
		assertNotNull(propostadto);
		Assertions.assertFalse(propostaservices.validaDocumentoIgualParaProposta(propostadto));
		Proposta proposta = propostadto.toModel();
		assertNotNull(proposta);
	}
	

}
