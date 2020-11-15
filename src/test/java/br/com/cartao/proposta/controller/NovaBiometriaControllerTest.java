package br.com.cartao.proposta.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.Validator;

import javax.validation.ValidatorFactory;

@ActiveProfiles("test")
class NovaBiometriaControllerTest {

    private static Validator validator;
    private static ValidatorFactory validatorFactory;

    @BeforeEach
    void setup(){

    }

    @Test
    void criaBiometria() {


    }
}