package com.github.marcoscoutozup.proposta.validator;

import com.github.marcoscoutozup.proposta.validator.base64.Base64Validator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class Base64Tests {

    private Base64Validator base64Validator;

    @Before
    public void setup(){
        base64Validator = new Base64Validator();
        base64Validator.initialize(null);
    }

    @Test
    @DisplayName("Deve aceitar o informação Base64")
    public void deveAceitarCpfComPontuacao(){
        boolean resultado = base64Validator.isValid("1234", null);
        Assert.assertTrue(resultado);
    }

    @Test
    @DisplayName("Não deve aceitar valor nulo")
    public void deveAceitarCpfSemPontuacao(){
        boolean resultado = base64Validator.isValid(null, null);
        Assert.assertFalse(resultado);
    }

    @Test
    @DisplayName("Não deve aceitar Base64 inválido")
    public void deveAceitarCnpjComPontuacao(){
        boolean resultado = base64Validator.isValid("1234$$", null);
        Assert.assertFalse(resultado);
    }

}
