package br.com.zup.cartaoproposta;

import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import br.com.zup.cartaoproposta.entities.proposta.PropostaNovoRequest;
import br.com.zup.cartaoproposta.validations.cpfcnpj.CpfCnpj;
import br.com.zup.cartaoproposta.validations.cpfcnpj.CpfCnpjValidador;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartaoPropostaApplicationTests {

	@Test
	@DisplayName("deve criar uma entidade proposta")
	void propostaNovoRequestToModel() {

		String documentoApenasNumeros = "50756719000125";
		String documentoValido = "50.756.719/0001-25";
		String emailValido = "teste@email.com";
		String nomeValido = "Nome 1";
		String enderecoValido = "Endereço 1";
		BigDecimal salarioValido = new BigDecimal("1500.00");

		PropostaNovoRequest novaProposta = new PropostaNovoRequest(documentoValido,emailValido,nomeValido,enderecoValido,salarioValido);
		Proposta proposta = novaProposta.toModel();

		assertEquals(documentoApenasNumeros,proposta.getDocumento());
		assertEquals(emailValido,proposta.getEmail());
		assertEquals(nomeValido,proposta.getNome());
		assertEquals(enderecoValido,proposta.getEndereco());
		assertEquals(salarioValido,proposta.getSalario());
	}

	@Test
	@DisplayName("não aceitar documento invalido")
	void documentoInvalido() {
		CpfCnpjValidador validador = new CpfCnpjValidador();

		boolean valido = validador.isValid("00000000000", null);
		assertFalse(valido);

		valido = validador.isValid("50.756.719/0001-26", null);
		assertFalse(valido);

		valido = validador.isValid("", null);
		assertFalse(valido);
	}

	@Test
	@DisplayName("aceitar cpf")
	void documentoValidoCpf() {
		CpfCnpjValidador validador = new CpfCnpjValidador();

		boolean valido = validador.isValid("064.388.700-80", null);
		assertTrue(valido);

		valido = validador.isValid("69714597099", null);
		assertTrue(valido);
	}

	@Test
	@DisplayName("aceitar cnpj")
	void documentoValidoCnpj() {
		CpfCnpjValidador validador = new CpfCnpjValidador();

		boolean valido = validador.isValid("35.599.966/0001-71", null);
		assertTrue(valido);

		valido = validador.isValid("95172016000198", null);
		assertTrue(valido);
	}
}
