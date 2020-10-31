package br.com.proposta.testesValidacoes;

import br.com.proposta.entidades.Aviso;
import br.com.proposta.entidades.Enums.StatusAvaliacaoProposta;
import br.com.proposta.entidades.Enums.StatusAviso;
import br.com.proposta.entidades.Proposta;
import br.com.proposta.validacoes.interfaces.CpfCnpj;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;

public class ValidacoesPropostasTestes {


    /*  Validações testadas:
     *  1 - nome da proposta não pode ser nem nulo nem branco
     *  2 - o email da proposta não pode ser nulo ou branco e deve ter formato de email
     *  3 - o endereço não pode ser nem nulo nem branco
     *  4 - o valor do salário não pode ser nulo
     * */

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("nome da proposta não pode ser nem nulo nem branco")
    public void deveriaRejeitarNomeDaPropostaEmBrancoOuNulo() {

        var proposta =
                new Proposta(" ", "teste@email.com", "teste teste teste", new BigDecimal(10000), "123.123.123-01");


        Set<ConstraintViolation<Proposta>> violations = validator.validate(proposta);

        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    @DisplayName("o email da proposta não pode ser nulo ou branco e deve ter formato de email")
    public void deveriaRejeitarEmailDaPropostaEmBrancoOuNulo() {

        var proposta =
                new Proposta("Teste testando", " ", "teste teste teste", new BigDecimal(10000), "123.123.123-01");


        Set<ConstraintViolation<Proposta>> violations = validator.validate(proposta);

        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    @DisplayName("o endereço não pode ser nem nulo nem branco")
    public void deveriaRejeitarEndereçoDaPropostaEmBrancoOuNulo() {

        var proposta =
                new Proposta("Teste testando", "teste@email.com", " ", new BigDecimal(10000), "123.123.123-01");


        Set<ConstraintViolation<Proposta>> violations = validator.validate(proposta);

        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    @DisplayName("o valor do salário não pode ser nulo")
    public void deveriaRejeitarValorDoSalarioNegativoDaProposta() {

        var proposta =
                new Proposta("Teste testando", "teste@email.com", "teste teste teste", new BigDecimal(-1), "123.123.123-01");


        Set<ConstraintViolation<Proposta>> violations = validator.validate(proposta);

        Assert.assertTrue(!violations.isEmpty());
    }


}
