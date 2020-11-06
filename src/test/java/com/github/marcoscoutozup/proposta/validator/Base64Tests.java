package com.github.marcoscoutozup.proposta.validator;

import com.github.marcoscoutozup.proposta.validator.base64.Base64Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Base64Tests {

    private Base64Validator base64Validator;

    @BeforeEach
    public void setup(){
        base64Validator = new Base64Validator();
        base64Validator.initialize(null);
    }

    @Test
    @DisplayName("Deve aceitar o informação Base64")
    public void deveAceitarCpfComPontuacao(){
        boolean resultado = base64Validator.isValid("1234", null);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Não deve aceitar valor nulo")
    public void deveAceitarCpfSemPontuacao(){
        boolean resultado = base64Validator.isValid(null, null);
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Não deve aceitar Base64 inválido")
    public void deveAceitarCnpjComPontuacao(){
        boolean resultado = base64Validator.isValid("1234$$", null);
        assertFalse(resultado);
    }

}
