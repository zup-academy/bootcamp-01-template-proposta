package br.com.proposta.testesValidacoes;

import br.com.proposta.entidades.Proposta;
import br.com.proposta.entidades.utils.IdentificacaoDescriptografada;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
                new Proposta(" ", "teste@email.com", "teste teste teste", new BigDecimal(10000), new IdentificacaoDescriptografada("123.123.123-01"));


        Set<ConstraintViolation<Proposta>> violations = validator.validate(proposta);

        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    @DisplayName("o email da proposta não pode ser nulo ou branco e deve ter formato de email")
    public void deveriaRejeitarEmailDaPropostaEmBrancoOuNulo() {

        var proposta =
                new Proposta("Teste Testando", " ", "teste teste teste", new BigDecimal(10000), new IdentificacaoDescriptografada("123.123.123-01"));


        Set<ConstraintViolation<Proposta>> violations = validator.validate(proposta);

        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    @DisplayName("o endereço não pode ser nem nulo nem branco")
    public void deveriaRejeitarEndereçoDaPropostaEmBrancoOuNulo() {

        var proposta =
                new Proposta("Teste Testando", "teste@email.com", " ", new BigDecimal(10000), new IdentificacaoDescriptografada("123.123.123-01"));


        Set<ConstraintViolation<Proposta>> violations = validator.validate(proposta);

        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    @DisplayName("o valor do salário não pode ser nulo nem negativo")
    public void deveriaRejeitarValorDoSalarioNegativoDaProposta() {

        var proposta =
                new Proposta("Teste Testando", "teste@email.com", "teste teste teste", new BigDecimal(-10000), new IdentificacaoDescriptografada("123.123.123-01"));


        Set<ConstraintViolation<Proposta>> violations = validator.validate(proposta);

        Assert.assertTrue(!violations.isEmpty());
    }


}
