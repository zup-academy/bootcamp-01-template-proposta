package br.com.itau.cartaobrancoproposta.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class CpfOuCnpjValidatorTest {

    private final CpfOuCnpjValidator cpfOuCnpj = new CpfOuCnpjValidator();

    @Test
    @DisplayName("não deve aceitar quando não é cpf ou cnpj")
    void cpfOuCnpjNaoDeveSerValido() {
        boolean valido = cpfOuCnpj.isValid("", null);

        Assert.state(!valido, "CPF ou CNPJ não deveria válido");
    }

    @Test
    @DisplayName("deve aceitar quando é cpf")
    void cpfDeveSerValido() {
        boolean valido = cpfOuCnpj.isValid("330.547.310-06", null);

        Assert.isTrue(valido, "CPF deveria ser válido");
    }

    @Test
    @DisplayName("não deve aceitar quando é cpf sem pontuação")
    void cpfSemPontuacaoNaoDeveSerValido() {
        boolean valido = cpfOuCnpj.isValid("33054731006", null);

        Assert.state(!valido, "CPF não deveria ser válido");
    }

    @Test
    @DisplayName("deve aceitar quando é cnpj")
    void cnpjDeveSerValido() {
        boolean valido = cpfOuCnpj.isValid("83.737.146/0001-41", null);

        Assert.isTrue(valido, "CNPJ deveria ser válido");
    }

    @Test
    @DisplayName("não deve aceitar quando é cnpj sem pontuação")
    void cnpjSemPontuacaoNaoDeveSerValido() {
        boolean valido = cpfOuCnpj.isValid("83737146000141", null);

        Assert.state(!valido, "CPF não deveria ser válido");
    }
}
