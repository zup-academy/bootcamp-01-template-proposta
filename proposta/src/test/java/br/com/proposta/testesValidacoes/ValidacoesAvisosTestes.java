package br.com.proposta.testesValidacoes;


import br.com.proposta.entidades.Aviso;
import br.com.proposta.entidades.Enums.StatusAviso;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidacoesAvisosTestes {

    /* Validações testadas:

    *  1 - número do cartão não pode estar em branco nem ser nulo
    *  2 - registro de data do aviso não pode ser nulo
    *  3 - o IP não pode estar me branco nem ser nulo
    *  4 - o userAgente não pode estar em branco nem ser nulo
    *  5 - o status deve receber dois valores
    *
    * */

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("o aviso não pode ter número de cartão em branco ou nulo")
    public void deveriaRejeitarNumeroDeCartaoEmBrancoOuNulo() {

        Aviso aviso = new Aviso(" ",
                "127.0.0.1", "insomnia/2020.4.1", StatusAviso.CRIADO);

        Set<ConstraintViolation<Aviso>> violations = validator.validate(aviso);

        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    @DisplayName("registro de data do aviso não pode ser nulo")
    public void registroDeDataDoAvisoNãoPodeSerNulo() {

        Aviso aviso = new Aviso("c29b096f-f094-4963-ad75-96c4493c2bdb",
                "127.0.0.1", "insomnia/2020.4.1", StatusAviso.CRIADO);

        Set<ConstraintViolation<Aviso>> violations = validator.validate(aviso);

        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    @DisplayName("o IP não pode estar me branco nem ser nulo")
    public void oIPNaoPodeEstarEmBrancoNemSerNulo() {

        Aviso aviso = new Aviso("c29b096f-f094-4963-ad75-96c4493c2bdb",
                "127.0.0.1", "insomnia/2020.4.1", StatusAviso.CRIADO);

        Set<ConstraintViolation<Aviso>> violations = validator.validate(aviso);

        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    @DisplayName("o userAgente não pode estar em branco nem ser nulo")
    public void oUserAgentNaoPodeEstarEmBrancoNemSerNulo() {

        Aviso aviso = new Aviso("c29b096f-f094-4963-ad75-96c4493c2bdb",
                "127.0.0.1", "insomnia/2020.4.1", StatusAviso.CRIADO);

        Set<ConstraintViolation<Aviso>> violations = validator.validate(aviso);

        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    @DisplayName("o status deve receber dois valores")
    public void oStatusDeveReceberDoisValores() {

        Aviso aviso1 = new Aviso("c29b096f-f094-4963-ad75-96c4493c2bdb",
                "127.0.0.1", "insomnia/2020.4.1", StatusAviso.CRIADO);

        Aviso aviso2 = new Aviso("c29b096f-f094-4963-ad75-96c4493c2bdb",
                "127.0.0.1", "insomnia/2020.4.1", StatusAviso.NAO_CRIADO);

        Assert.assertNull(aviso1);
        Assert.assertNull(aviso2);
    }

}
