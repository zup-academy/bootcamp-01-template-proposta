package com.github.marcoscoutozup.proposta.carteira;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class CarteiraTests {

    private Carteira carteira;

    @Before
    public void setup(){
        carteira = new Carteira();
    }

    @Test
    @DisplayName("Não deve verificar carteira se for nula")
    public void naoDeveVerificarCarteiraSeForNula() {
        Assert.assertThrows(IllegalArgumentException.class, () -> carteira.verificarParidadeDeCarteira(null));
    }

}
