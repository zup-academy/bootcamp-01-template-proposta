package com.github.marcoscoutozup.proposta.proposta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PropostaTests {

    private Proposta proposta;

    @BeforeEach
    public void setup(){
        proposta = new Proposta();
    }

    @Test
    @DisplayName("Não deve modificar status da proposta se ela for nula")
    public void naoDeveModificarStatusDaPropostaSeElaForNula() {
        assertThrows(IllegalArgumentException.class, () -> proposta.modificarStatusDaProposta(null));
    }
}
