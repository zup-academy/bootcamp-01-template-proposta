package br.com.zup.cartaoproposta;

import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import br.com.zup.cartaoproposta.entities.proposta.PropostaNovoRequest;
import br.com.zup.cartaoproposta.validations.cpfcnpj.CpfCnpj;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CartaoPropostaApplicationTests {

	private final String documentoApenasNumeros = "50756719000125";
	private final String documentoValido = "50.756.719/0001-25";
	private final String emailValido = "teste@email.com";
	private final String nomeValido = "Nome 1";
	private final String enderecoValido = "Endereço 1";
	private final BigDecimal salarioValido = new BigDecimal("1500.00");

	@Test
	void propostaNovoRequestToModel() {
		PropostaNovoRequest novaProposta = new PropostaNovoRequest(documentoValido,emailValido,nomeValido,enderecoValido,salarioValido);
		Proposta proposta = novaProposta.toModel();

		assertEquals(documentoApenasNumeros,proposta.getDocumento());
		assertEquals(emailValido,proposta.getEmail());
		assertEquals(nomeValido,proposta.getNome());
		assertEquals(enderecoValido,proposta.getEndereco());
		assertEquals(salarioValido,proposta.getSalario());
	}
}
