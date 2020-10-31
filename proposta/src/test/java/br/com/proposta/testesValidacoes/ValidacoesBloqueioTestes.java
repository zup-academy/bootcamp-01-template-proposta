package br.com.proposta.testesValidacoes;
import br.com.proposta.entidades.Bloqueio;
import br.com.proposta.entidades.Enums.StatusBloqueio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidacoesBloqueioTestes {


    /* Validações testadas:
     *  1 - o IP não pode estar me branco nem ser nulo
     *  2 - o userAgente não pode estar em branco nem ser nulo
     *  3 - o status deve receber dois valores
     * */


    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    @DisplayName("o IP não pode estar me branco nem ser nulo")
    public void oIPNaoPodeEstarEmBrancoNemSerNulo() {

        Bloqueio bloqueio =
                new Bloqueio(" ", "insomnia/2020.4.1", StatusBloqueio.BLOQUEADO);

        Set<ConstraintViolation<Bloqueio>> violations = validator.validate(bloqueio);

        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    @DisplayName("o userAgente não pode estar em branco nem ser nulo")
    public void oUserAgentNaoPodeEstarEmBrancoNemSerNulo() {

        Bloqueio bloqueio =
                new Bloqueio("127.0.0.1", " ", StatusBloqueio.BLOQUEADO);

        Set<ConstraintViolation<Bloqueio>> violations = validator.validate(bloqueio);

        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    @DisplayName("o status deve receber dois valores")
    public void oStatusDeveReceberDoisValores() {

        Bloqueio bloqueio1 =
                new Bloqueio("127.0.0.1", " ", StatusBloqueio.BLOQUEADO);

        Bloqueio bloqueio2 =
                new Bloqueio("127.0.0.1", " ", StatusBloqueio.DESBLOQUEADO);

        Assert.assertNotNull(bloqueio1);
        Assert.assertNotNull(bloqueio2);

    }
}
