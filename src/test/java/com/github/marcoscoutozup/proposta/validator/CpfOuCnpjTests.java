package com.github.marcoscoutozup.proposta.validator;

import com.github.marcoscoutozup.proposta.validator.cpfoucnpj.CpfOuCnpjValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CpfOuCnpjTests {

            //1
    private CpfOuCnpjValidator cpfOuCnpjValidator;

    @BeforeEach
    public void setup(){
        cpfOuCnpjValidator = new CpfOuCnpjValidator();
        cpfOuCnpjValidator.initialize(null);
    }

    @Test
    @DisplayName("Deve aceitar CPF com Pontuação")
    public void deveAceitarCpfComPontuacao(){
        boolean resultado = cpfOuCnpjValidator.isValid("492.581.220-38", null);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Deve aceitar CPF sem Pontuação")
    public void deveAceitarCpfSemPontuacao(){
        boolean resultado = cpfOuCnpjValidator.isValid("49258122038", null);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Deve aceitar CNPJ com Pontuação")
    public void deveAceitarCnpjComPontuacao(){
        boolean resultado = cpfOuCnpjValidator.isValid("96.712.698/0001-47", null);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Deve aceitar CNPJ sem Pontuação")
    public void deveAceitarCnpjSemPontuacao(){
        boolean resultado = cpfOuCnpjValidator.isValid("96712698000147", null);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Não deve aceitar CPF ou CNPJ inválido")
    public void naoDeveValidarCpfOuCnpjInvalido(){
        boolean resultado = cpfOuCnpjValidator.isValid("1234", null);
        assertFalse(resultado);
    }

}
