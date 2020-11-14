package com.github.marcoscoutozup.proposta.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CryptoTests {

    @Test
    @DisplayName("Deve lançar exception se informação para criptografia for nula")
    public void deveLancaoExceptionSeInformacaoParaCriptografiaForNula(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> Crypto.encrypt(null));
    }
}
