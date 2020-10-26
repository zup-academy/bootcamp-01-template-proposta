package br.com.proposta.novapropostatestes;

import br.com.proposta.models.Proposta;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

public class SalarioBrutoNaoPodeSerVazio {

    private Validator validator;

    private Proposta novaProposta;


    @Before
    public void setUp() {

        novaProposta = new Proposta("Nome", "teste@teste.com", "Endereço teste", new BigDecimal(0), "123.456.789");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();

    }


    @Test
    public void nomeNaoDeveSerEmBrancoNoCadastro(){

        Set<ConstraintViolation<Proposta>> violations = validator.validate(novaProposta);

        Assert.assertTrue(!violations.isEmpty());

    }

}
