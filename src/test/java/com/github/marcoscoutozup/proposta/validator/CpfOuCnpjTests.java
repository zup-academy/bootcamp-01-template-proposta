package com.github.marcoscoutozup.proposta.validator;

import com.github.marcoscoutozup.proposta.validator.cpfoucnpj.CpfOuCnpjValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class CpfOuCnpjTests {

    private CpfOuCnpjValidator cpfOuCnpjValidator;

    @Before
    public void setup(){
        cpfOuCnpjValidator = new CpfOuCnpjValidator();
        cpfOuCnpjValidator.initialize(null);
    }

    @Test
    @DisplayName("Deve aceitar CPF com Pontuação")
    public void deveAceitarCpfComPontuacao(){
        boolean resultado = cpfOuCnpjValidator.isValid("492.581.220-38", null);
        Assert.assertTrue(resultado);
    }

    @Test
    @DisplayName("Deve aceitar CPF sem Pontuação")
    public void deveAceitarCpfSemPontuacao(){
        boolean resultado = cpfOuCnpjValidator.isValid("49258122038", null);
        Assert.assertTrue(resultado);
    }

    @Test
    @DisplayName("Deve aceitar CNPJ com Pontuação")
    public void deveAceitarCnpjComPontuacao(){
        boolean resultado = cpfOuCnpjValidator.isValid("96.712.698/0001-47", null);
        Assert.assertTrue(resultado);
    }

    @Test
    @DisplayName("Deve aceitar CNPJ sem Pontuação")
    public void deveAceitarCnpjSemPontuacao(){
        boolean resultado = cpfOuCnpjValidator.isValid("96712698000147", null);
        Assert.assertTrue(resultado);
    }

    @Test
    @DisplayName("Não deve aceitar CPF ou CNPJ inválido")
    public void naoDeveValidarCpfOuCnpjInvalido(){
        boolean resultado = cpfOuCnpjValidator.isValid("1234", null);
        Assert.assertFalse(resultado);
    }

}
