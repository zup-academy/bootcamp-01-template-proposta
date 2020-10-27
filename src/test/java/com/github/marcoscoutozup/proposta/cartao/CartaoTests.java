package com.github.marcoscoutozup.proposta.cartao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class CartaoTests {

    private Cartao cartao;

    @Before
    public void setup(){
        cartao = new Cartao();
    }

    @Test
    @DisplayName("Não deve incluir biometria no cartão se for nula")
    public void naoDeveIncluirBiometriaNoCartaoSeForNula() {
        Assert.assertThrows(IllegalArgumentException.class, () -> cartao.incluirBiometriaNoCartao(null));
    }

    @Test
    @DisplayName("Não deve incluir bloqueio no cartão se for nulo")
    public void naoDeveIncluirBloqueioNoCartaoSeForNulo() {
        Assert.assertThrows(IllegalArgumentException.class, () -> cartao.incluirBloqueioDoCartao(null));
    }

    @Test
    @DisplayName("Não deve incluir aviso viagem no cartão se for nulo")
    public void naoDeveIncluirAvisoViagemNoCartaoSeForNulo() {
        Assert.assertThrows(IllegalArgumentException.class, () -> cartao.incluirAvisoDeViagem(null));
    }

    @Test
    @DisplayName("Não deve incluir aviso viagem no cartão se for nulo")
    public void naoDeveIncluirCarteiraNoCartaoSeForNula() {
        Assert.assertThrows(IllegalArgumentException.class, () -> cartao.incluirCarteira(null));
    }

    @Test
    @DisplayName("Não deve verificar carteira se for nula")
    public void naoDeveVerificarCarteiraSeForNula() {
        Assert.assertThrows(IllegalArgumentException.class, () -> cartao.verificarSeJaExisteAssociacaoDaCarteiraComOCartao(null));
    }

}
