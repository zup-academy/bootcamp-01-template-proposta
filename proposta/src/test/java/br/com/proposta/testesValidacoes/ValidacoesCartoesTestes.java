package br.com.proposta.testesValidacoes;
import br.com.proposta.entidades.Cartao;
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


public class ValidacoesCartoesTestes {


    /* Validações testadas:
     *  1 - o titular não pode estar em branco ou nulo
     *  2 - o número não pode estar em branco ou nulo
     * */


    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    @DisplayName("o IP não pode estar me branco nem ser nulo")
    public void titularDoCartaoNaoPodeEstarEmBrancoOuNulo() {

        var proposta =
                new Proposta("Teste Testando", "teste@email.com", "teste teste teste", new BigDecimal(10000), new IdentificacaoDescriptografada("123.123.123-01"));

        var cartao =
                new Cartao("4ca82223-8c9b-4472-a565-a0280f2ee903", " ", proposta);

        Set<ConstraintViolation<Cartao>> violations = validator.validate(cartao);

        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    @DisplayName("o userAgente não pode estar em branco nem ser nulo")
    public void numeroDoCartaoNaoPodeEstarEmBrancoOuNulo() {

        var proposta =
                new Proposta("Teste Testando", "teste@email.com", "teste teste teste", new BigDecimal(10000), new IdentificacaoDescriptografada("123.123.123-01"));

        var cartao =
                new Cartao(" ", "Teste Testando", proposta);

        Set<ConstraintViolation<Cartao>> violations = validator.validate(cartao);

        Assert.assertTrue(!violations.isEmpty());
    }


}
