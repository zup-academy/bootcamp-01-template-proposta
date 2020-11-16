package dev.arielalvesdutra.proposta.test.unit.annotations.validators;

import dev.arielalvesdutra.proposta.annotations.validators.Base64Validator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Base64ValidatorTest {

    @Test
    public void parametroNulo_deveRetornarFalse() {
        var validador = new Base64Validator();
        validador.initialize(null);

        assertThat(validador.isValid(null,null)).isFalse();
    }

    @Test
    public void parametroVazio_deveRetornarFalse() {
        var validador = new Base64Validator();
        validador.initialize(null);

        assertThat(validador.isValid("",null)).isFalse();
    }
}
