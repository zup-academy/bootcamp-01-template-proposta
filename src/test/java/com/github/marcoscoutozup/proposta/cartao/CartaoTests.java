package com.github.marcoscoutozup.proposta.cartao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CartaoTests {

    private Cartao cartao;

    @BeforeEach
    public void setup(){
        cartao = new Cartao();
    }

    @Test
    @DisplayName("Não deve incluir biometria no cartão se for nula")
    public void naoDeveIncluirBiometriaNoCartaoSeForNula() {
        assertThrows(IllegalArgumentException.class,
                () -> cartao.incluirBiometriaNoCartao(null));
    }

    @Test
    @DisplayName("Não deve incluir bloqueio no cartão se for nulo")
    public void naoDeveIncluirBloqueioNoCartaoSeForNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> cartao.incluirBloqueioDoCartao(null));
    }

    @Test
    @DisplayName("Não deve incluir aviso viagem no cartão se for nulo")
    public void naoDeveIncluirAvisoViagemNoCartaoSeForNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> cartao.incluirAvisoDeViagem(null));
    }

    @Test
    @DisplayName("Não deve incluir carteira no cartão se for nula")
    public void naoDeveIncluirCarteiraNoCartaoSeForNula() {
        assertThrows(IllegalArgumentException.class,
                () -> cartao.incluirCarteira(null));
    }

    @Test
    @DisplayName("Não deve verificar carteira se for nula")
    public void naoDeveVerificarCarteiraSeForNula() {
        assertThrows(IllegalArgumentException.class,
                () -> cartao.verificarSeJaExisteAssociacaoDaCarteiraComOCartao(null));
    }

}
