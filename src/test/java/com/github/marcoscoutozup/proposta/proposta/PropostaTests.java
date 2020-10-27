package com.github.marcoscoutozup.proposta.proposta;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class PropostaTests {

    private Proposta proposta;

    @Before
    public void setup(){
        proposta = new Proposta();
    }

    @Test
    @DisplayName("Não deve modificar status da proposta se ela for nula")
    public void naoDeveModificarStatusDaPropostaSeElaForNula() {
        Assert.assertThrows(IllegalArgumentException.class, () -> proposta.modificarStatusDaProposta(null));
    }
}
