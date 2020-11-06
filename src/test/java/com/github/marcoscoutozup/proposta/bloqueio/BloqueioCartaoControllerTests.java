package com.github.marcoscoutozup.proposta.bloqueio;

import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BloqueioCartaoControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private BloqueioService bloqueioService;

    private BloquearCartaoController controller;

    @BeforeEach
    public void setup(){
        initMocks(this);
        controller = new BloquearCartaoController(entityManager, bloqueioService);
    }

    @Test
    @DisplayName("Não deve bloquear se cartão não foi encontrado")
    public void naoDeveBloquearSeCartaoNaoFoiEncontrado(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(null);
        ResponseEntity responseEntity = controller.bloquearCartao(UUID.randomUUID(), null, null);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardError);
    }

}
