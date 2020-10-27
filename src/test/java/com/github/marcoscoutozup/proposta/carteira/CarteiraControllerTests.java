package com.github.marcoscoutozup.proposta.carteira;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.carteira.enums.TipoCarteira;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CarteiraControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CarteiraService carteiraService;

    @Mock
    private Cartao cartao;

    private CarteiraController controller;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        controller = new CarteiraController(entityManager, carteiraService);
    }

    @Test
    @DisplayName("Não deve cadastrar carteira se cartão não foi encontrado")
    public void naoDeveCadastrarCarteiraSamsungPaySeCartaoNaoFoiEncontrado(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(null);
        ResponseEntity responseEntity = controller.processarSolicitacao(null, UUID.randomUUID(), null, null);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getBody() instanceof StandardError);
    }

    @Test
    @DisplayName("Não deve cadastrar a carteira SamsungPay se ela estiver cadastrada já estiver cadastradaa")
    public void naoDeveCadastrarCarteiraSamsungPaySeJaEstiverCadastrada(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(cartao);
        when(cartao.verificarSeJaExisteAssociacaoDaCarteiraComOCartao(TipoCarteira.SAMSUNG_PAY)).thenReturn(true);
        ResponseEntity responseEntity = controller.processarSolicitacao(TipoCarteira.SAMSUNG_PAY, UUID.randomUUID(), null, null);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getBody() instanceof StandardError);
    }

    @Test
    @DisplayName("Não deve cadastrar a carteira PayPal se ela estiver cadastrada já estiver cadastrada")
    public void naoDeveCadastrarCarteiraPayPalSeJaEstiverCadastrada(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(cartao);
        when(cartao.verificarSeJaExisteAssociacaoDaCarteiraComOCartao(TipoCarteira.PAYPAL)).thenReturn(true);
        ResponseEntity responseEntity = controller.processarSolicitacao(TipoCarteira.PAYPAL, UUID.randomUUID(), null, null);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getBody() instanceof StandardError);
    }

}
