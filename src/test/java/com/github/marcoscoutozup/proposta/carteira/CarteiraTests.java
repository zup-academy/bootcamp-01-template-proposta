package com.github.marcoscoutozup.proposta.carteira;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarteiraTests {

    private Carteira carteira;

    @BeforeEach
    public void setup(){
        carteira = new Carteira();
    }

    @Test
    @DisplayName("Não deve verificar carteira se for nula")
    public void naoDeveVerificarCarteiraSeForNula() {
        assertThrows(IllegalArgumentException.class,
                () -> carteira.verificarParidadeDeCarteira(null));
    }

}
