package com.github.marcoscoutozup.proposta.avisos;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.cartao.CartaoClient;
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

public class CadastrarAvisoControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CartaoClient cartaoClient;

    private CadastrarAvisoController controller;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        controller = new CadastrarAvisoController(entityManager, cartaoClient);
    }

    @Test
    @DisplayName("Não deve cadastrar aviso se cartão não foi encontrado")
    public void naoDeveCadastrarAvisoSeCartaoNaoFoiEncontrado(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(null);
        ResponseEntity responseEntity = controller.cadastrarBiometria(UUID.randomUUID(), null, null, null);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getBody() instanceof StandardError);
    }
}
