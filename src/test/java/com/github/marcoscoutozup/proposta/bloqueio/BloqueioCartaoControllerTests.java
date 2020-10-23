package com.github.marcoscoutozup.proposta.bloqueio;

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

public class BloqueioCartaoControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private BloqueioService bloqueioService;

    private BloquearCartaoController controller;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        controller = new BloquearCartaoController(entityManager, bloqueioService);
    }

    @Test
    @DisplayName("Não deve bloquear se cartão não foi encontrado")
    public void naoDeveBloquearSeCartaoNaoFoiEncontrado(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(null);
        ResponseEntity responseEntity = controller.bloquearCartao(UUID.randomUUID(), null, null);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getBody() instanceof StandardError);
    }

}
